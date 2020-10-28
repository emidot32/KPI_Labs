package com.systemsoftware.labs.lab2;


public class SymLink extends Link{
    String pathName;

    public SymLink(int id, String name, String pathName) {
        super(id, name);
        this.pathName = pathName;
    }

    @Override
    public String toString() {
        return "SymLink{" +
                "id=" + id +
                ", pathName='" + pathName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
