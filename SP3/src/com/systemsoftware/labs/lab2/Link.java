package com.systemsoftware.labs.lab2;

public abstract class Link extends FsEntity implements IEntityPart {
    String name;
    public Link(int id,  String name) {
        super(id);
        this.name = name;
    }
}
