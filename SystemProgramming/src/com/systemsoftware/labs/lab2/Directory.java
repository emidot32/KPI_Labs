package com.systemsoftware.labs.lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Directory extends FsEntity implements IEntityPart {
    String name;
    Directory parent;
    List<FsEntity> dirElements = new ArrayList<>();

    public Directory(int id, String name, Directory parent) {
        super(id);
        this.name = name;
        this.parent = parent;
    }

    void add(FsEntity element) {
        dirElements.add(element);
    }

    void remove(FsEntity element) {
        dirElements.remove(element);
    }

    Directory getDirByName(String name){
        return dirElements.stream()
                .filter(fsEntity -> fsEntity instanceof Directory dir && dir.name.equals(name))
                .findFirst()
                .map(fsEntity -> (Directory) fsEntity)
                .get();
    }

    SymLink getLinkByName(String name){
        return dirElements.stream()
                .filter(fsEntity -> fsEntity instanceof SymLink link && link.name.equals(name))
                .findFirst()
                .map(fsEntity -> (SymLink) fsEntity)
                .get();
    }


    boolean isEmpty(){
        return dirElements.isEmpty();
    }

    boolean hasDir(String name) {
        return dirElements.stream()
                .anyMatch(fsEntity->fsEntity instanceof Directory dir && dir.name.equals(name));
    }

    boolean hasFile(String name) {
        return dirElements.stream()
                .anyMatch(fsEntity->fsEntity instanceof File file && file.hasLink(name));
    }

    boolean hasLink(String name) {
        return dirElements.stream()
                .anyMatch(fsEntity->fsEntity instanceof SymLink link && link.name.equals(name));
    }

    boolean hasElement(String name){
        return hasLink(name) || hasFile(name) || hasDir(name);
    }

    String getAbsPath(){
        Directory curDir = this;
        List<String> list = new ArrayList<>();
        while (curDir.parent != null) {
            list.add(curDir.name);
            curDir = curDir.parent;
        }
        Collections.reverse(list);
        return "/"+String.join("/", list);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
