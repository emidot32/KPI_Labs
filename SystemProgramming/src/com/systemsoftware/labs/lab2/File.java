package com.systemsoftware.labs.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class File extends FsEntity {
    int size;
    Integer descriptor;
    String data;
    List<HardLink> links = new ArrayList<>();

    public File(int id, String name, String data) {
        super(id);
        this.data = data;
        this.size = (int) Math.ceil(data.length()/(float) DiskSpace.BLOCK_SIZE);
        HardLink link = new HardLink(Driver.id++,this, name);
        links.add(link);
        DiskSpace.blockFileTable[DiskSpace.getStartIndex(1)] = link;
    }

    boolean hasLink(String linkName){
        return links.stream()
            .anyMatch(link->link.name.equals(linkName));
    }

    HardLink getLink(String linkName){
        return links.stream()
                .filter(link->link.name.equals(linkName))
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", size=" + size +
                ", descriptor=" + descriptor +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
