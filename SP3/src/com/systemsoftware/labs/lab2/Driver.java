package com.systemsoftware.labs.lab2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.systemsoftware.labs.lab2.DiskSpace.*;

public class Driver {
    static int id = 2;
    static Directory currentDir;

    static {
        currentDir = new Directory(1, "/", null);
        blockFileTable[getStartIndex(1)] = currentDir;
    }

    static String fileSystem;
    static int descriptor = 0;
    final static int MAX_FILE_LENGTH = 32;
    static int limitLinkJump = 5;

    static void mount() {
        fileSystem = "NTFS";
    }

    static void unmount() {
        fileSystem = null;
    }

    static void ls() {
        currentDir.dirElements.forEach(System.out::println);
    }

    static void fileStat(String id) {
        Optional<File> optionalFile = getFilesFromDiskSpace()
                .stream()
                .filter(file -> Integer.parseInt(id) == file.descriptor)
                .findFirst();
        if (optionalFile.isPresent()) System.out.format("File with descriptor %s:\n%s\n", id, optionalFile.get());
        else System.out.format("File with descriptor %s is not found!\n", id);
    }

    static void createFile(String filename, String data) {
        if (filename.startsWith("/")){
            String[] split = filename.split("/");
            Directory dir = jumpToLink(String.join("/", Arrays.asList(Arrays.copyOf(split, split.length-1))));
            if (dir != null) {
                createFile(split[split.length-1], data, dir);
            } else System.out.println("No such directory!");
        } else createFile(filename, data, currentDir);
        System.out.println("File is created!");
    }

    private static void createFile(String filename, String data, Directory dir){
        if (filename.length() > MAX_FILE_LENGTH) {
            System.out.println("File name length too long!");
            return;
        }
        if (dir.hasFile(filename)) {
            System.out.format("File with name '%s' is exists!\n", filename);
            return;
        }
        File file = new File(id++, filename, data);
        dir.add(file);
        int startIndex = getStartIndex(file.size);
        if (startIndex == -1) {
            System.out.println("No free space!");
            return;
        }
        int index = 0;
        for (int i = startIndex; i < (startIndex + file.size); i++) {
            blockFileTable[i] = new FilePart(file, index, Math.min(file.data.length(), index + 4));
            index += 4;
        }
    }

    static void open(String fileName) {
        if (currentDir.hasLink(fileName)){
            String[] split = getSymLinkByNameFromDiskSpace(fileName).pathName.split("/");
            if (limitLinkJump-- != 0) {
                open(split[split.length - 1]);
            }
            else {
                limitLinkJump=5;
                System.out.println("No such file or link!");
            }
        } else if (isFile(fileName)){
            String[] newFileName = new String[]{fileName};
            if (fileName.startsWith("/")){
                String[] split = fileName.split("/");
                newFileName[0] = split[split.length-1];
            }
            getFilesFromDiskSpace()
                    .stream()
                    .filter(file -> file.hasLink(newFileName[0]))
                    .findFirst()
                    .ifPresent(file -> {
                        file.descriptor = ++descriptor;
                        System.out.format("File data: %s\nDescriptor: %d\n", file.data, file.descriptor);
                    });
        } else System.out.println("No such file or link!");
    }

    static void close(String fd) {
        getFilesFromDiskSpace()
                .stream()
                .filter(file -> file.descriptor.equals(Integer.parseInt(fd)))
                .findFirst()
                .ifPresent(file -> {
                    file.descriptor = null;
                    descriptor--;
                    System.out.format("File '%s' is closed\n", file.id);
                });
    }

    static void read(String fd, String shift) {
        getFilesFromDiskSpace()
                .stream()
                .filter(file -> file.descriptor.equals(Integer.parseInt(fd)))
                .findFirst()
                .ifPresent(file -> {
                    int shiftInt = Integer.parseInt(shift);
                    if (shiftInt > file.data.length()) System.out.println("Shift is larger then data length!");
                    else System.out.format("File data with shift %s: %s\n", shift,
                            file.data.substring(shiftInt));

                });
    }

    static void write(String fd, String shift, String data) {
        getFilesFromDiskSpace()
                .stream()
                .filter(file -> file.descriptor.equals(Integer.parseInt(fd)))
                .findFirst()
                .ifPresent(file -> {
                    int shiftInt = Integer.parseInt(shift);
                    if (shiftInt > file.data.length()) System.out.println("Shift is larger then data length!");
                    else {
                        String newData = file.data.substring(0, shiftInt) + data;
                        if (newData.length() > file.data.length()) {
                            System.out.println("New string more then the old one. Increase size using truncate");
                        } else {
                            file.data = newData + " ".repeat(file.data.length() - newData.length());
                            for (IEntityPart iEntityPart : blockFileTable) {
                                if (iEntityPart instanceof FilePart fp && fp.file.equals(file)) {
                                    fp.dataPart = file.data.substring(fp.startIndex, fp.endIndex);
                                }
                            }
                            System.out.format("New file data: %s\n", file.data);
                        }
                    }
                });
    }

    static void link(String fileName, String linkName) {
        Stream<File> fileStream = currentDir.dirElements
                .stream()
                .filter(fsEntity -> fsEntity instanceof File file && file.hasLink(fileName))
                .map(fsEntity -> (File) fsEntity);
        if (fileStream.count() > 1) {
            System.out.println("Hard link with this name exists!");
        } else {
            fileStream
                    .findFirst()
                    .ifPresent(file -> {
                        HardLink link = new HardLink(id++, file, linkName);
                        file.links.add(link);
                        currentDir.add(link);
                        blockFileTable[getStartIndex(1)] = link;
                        System.out.format("Link %s to file '%s' is created!\n", linkName, fileName);
                    });
        }
    }

    static void unlink(String linkName) {
        currentDir.dirElements
                .stream()
                .filter(fsEntity -> fsEntity instanceof File file && file.hasLink(linkName))
                .map(fsEntity -> (File) fsEntity)
                .findFirst()
                .ifPresent(file -> {
                    HardLink link = file.getLink(linkName);
                    file.links.remove(link);
                    for (int i = 0; i < blockFileTable.length; i++) {
                        if (blockFileTable[i] instanceof HardLink hl && link.id == hl.id) {
                            blockFileTable[i] = null;
                        }
                    }
                    currentDir.remove(link);
                    System.out.format("Link '%s' is deleted!\n", linkName);
                    if (file.links.isEmpty()) {
                        for (int i = 0; i < blockFileTable.length; i++) {
                            if (blockFileTable[i] instanceof FilePart fp && fp.file.id == file.id)
                                blockFileTable[i] = null;
                        }
                        currentDir.remove(file);
                        System.out.format("File '%s' is deleted!\n", file.id);
                    }
                });
    }

    static void truncate(String fileName, String newSize) {
        int newSizeInt = Integer.parseInt(newSize);
        if (fileName.startsWith("/")) {
            String[] split = fileName.split("/");
            truncateMain(split[split.length - 1], newSizeInt);
        } else if (currentDir.hasFile(fileName)) {
            truncateMain(fileName, newSizeInt);
        } else if (currentDir.hasLink(fileName)) {
            String[] split = currentDir.getLinkByName(fileName).pathName.split("/");
            if (isSymLink(split[split.length-1]) && limitLinkJump-- != 0)
                truncate(getSymLinkByNameFromDiskSpace(split[split.length-1]).pathName, newSize);
            else if (isFile(split[split.length-1])) truncateMain(split[split.length-1], newSizeInt);
            else System.out.println("No such file or link!");
        } else System.out.println("No such file or link!");
    }

    private static void truncateMain(String fileName, int newSizeInt) {
        for (int i = 0; i < blockFileTable.length; i++) {
            if (blockFileTable[i] != null && blockFileTable[i] instanceof FilePart fp) {
                File file = fp.file;
                if (file.hasLink(fileName)) {
                    if (newSizeInt < file.size) {
                        for (int j = 1; j <= file.size - newSizeInt; j++) {
                            blockFileTable[i + file.size - j] = null;
                        }
                        file.data = file.data.substring(0, newSizeInt * BLOCK_SIZE);
                        System.out.format("File size is decreased to %d for file '%s'\n", newSizeInt, fileName);
                    } else if (newSizeInt > file.size) {
                        int startIndex = getStartIndex(newSizeInt);
                        if (startIndex == -1) {
                            System.out.println("No free space!");
                            return;
                        }
                        int index = i;
                        for (int j = startIndex; j < startIndex + file.size; j++) {
                            blockFileTable[j] = blockFileTable[index];
                            blockFileTable[index++] = null;
                        }
                        int index2 = file.data.length();
                        file.data += " ".repeat((newSizeInt - file.size) * BLOCK_SIZE);
                        for (int j = startIndex + file.size; j < startIndex + newSizeInt; j++) {
                            blockFileTable[j] = new FilePart(file, index2, Math.min(file.data.length(), index2 + 4));
                            index2 += 4;
                        }
                        System.out.format("File size is increased to %d for file '%s'\n", newSizeInt, fileName);
                    }
                    file.size = newSizeInt;
                    break;
                }
            }
        }
    }
    
    static void mkDir(String dirName) {
        if (!currentDir.hasElement(dirName)) {
            Directory newDir = new Directory(id++, dirName, currentDir);
            currentDir.add(newDir);
            blockFileTable[getStartIndex(1)] = newDir;
            System.out.format("Directory '%s' is created!\n", dirName);
        } else System.out.format("Directory with name '%s' exists\n", dirName);
    }

    static void rmDir(String dirName) {
        if (currentDir.hasDir(dirName)) {
            Directory dir = currentDir.getDirByName(dirName);
            if (currentDir.getDirByName(dirName).isEmpty()) {
                currentDir.remove(dir);
                System.out.format("Directory '%s' is removed!\n", dirName);
            } else System.out.println("Directory is not empty!");
        } else System.out.format("Directory with name '%s' does not exist\n", dirName);
    }

    static void cd(String dirName) {
        if ("..".equals(dirName)) {
            if (currentDir.parent != null) {
                currentDir = currentDir.parent;
            }
        } else {
            if (dirName.startsWith("/")) {
                Directory dir = jumpToLink(dirName);
                if (dir != null) currentDir = dir;
            } else if (currentDir.hasDir(dirName)) {
                currentDir = currentDir.getDirByName(dirName);
            } else if (currentDir.hasLink(dirName)) {
                SymLink symLink = currentDir.getLinkByName(dirName);
                if (symLink.pathName.startsWith("/")) {
                    Directory dir = jumpToLink(symLink.pathName);
                    if (dir != null) currentDir = dir;
                } else if (currentDir.hasDir(symLink.pathName)) {
                    currentDir = currentDir.getDirByName(symLink.pathName);
                } else System.out.println("Directory does not exist!");
            } else System.out.println("Directory does not exist!");
        }
    }

    private static Directory jumpToLink(String name) {
        String[] dirs = name.substring(1).split("/");
        Directory tempDir = currentDir;
        for (int i = 0; i < dirs.length; i++) {
            if (tempDir.hasDir(dirs[i])) {
                tempDir = tempDir.getDirByName(dirs[i]);
            } else if (i == (dirs.length - 1) && tempDir.hasLink(dirs[i]) && limitLinkJump-- != 0) {
                cd(dirs[i]);
            } else {
                limitLinkJump = 5;
                System.out.println("No such file or directory!");
            }
        }
        if (limitLinkJump != 0) return tempDir;
        return null;
    }

    static void symLink(String fileOrDirName, String linkName) {
        if (!currentDir.hasElement(linkName)) {
            SymLink symLink = new SymLink(id++, linkName, fileOrDirName);
            blockFileTable[getStartIndex(1)] = symLink;
            currentDir.add(symLink);
            System.out.format("Sym link '%s' is created!\n", linkName);
        } else System.out.println("Link, dir or file with this name exists!");
    }

    private static Directory getRootDir(Directory dir) {
        if (dir.parent == null) return dir;
        return getRootDir(dir.parent);
    }

    private static Set<File> getFilesFromDiskSpace() {
        return Arrays.stream(blockFileTable)
                .filter(iEntityPart -> iEntityPart instanceof FilePart)
                .map(iEntityPart -> ((FilePart) iEntityPart).file)
                .collect(Collectors.toSet());
    }

    private static boolean isSymLink(String name){
        return Arrays.stream(blockFileTable)
                .anyMatch(iEntityPart -> iEntityPart instanceof SymLink sl && sl.name.equals(name));
    }
    private static SymLink getSymLinkByNameFromDiskSpace(String name){
        return Arrays.stream(blockFileTable)
                .filter(iEntityPart -> iEntityPart instanceof SymLink sl && sl.name.equals(name))
                .findFirst()
                .map(iEntityPart -> (SymLink) iEntityPart)
                .get();
    }

    private static boolean isFile(String name){
        return Arrays.stream(blockFileTable)
                .anyMatch(iEntityPart -> iEntityPart instanceof FilePart fp && fp.file.hasLink(name));
    }
    private static File getFileByNameFromDiskSpace(String name){
        return Arrays.stream(blockFileTable)
                .filter(iEntityPart -> iEntityPart instanceof FilePart fp && fp.file.hasLink(name))
                .findFirst()
                .map(iEntityPart -> ((FilePart) iEntityPart).file)
                .get();
    }
}
