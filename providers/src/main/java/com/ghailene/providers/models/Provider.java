package com.ghailene.providers.models;

public class Provider {

    private String id;
    private String name;

    public Provider(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Provider() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
