package com.systemsoftware.labs.lab2;


public class HardLink extends Link {
    File file;

    public HardLink(int id, File file, String name) {
        super(id, name);
        this.file = file;
    }

    @Override
    public String toString() {
        return "HardLink{" +
                "id=" + id +
                ", fileId=" + file.id +
                ", name='" + name + '\'' +
                '}';
    }
}
