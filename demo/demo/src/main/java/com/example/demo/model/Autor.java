package com.example.demo.model;

public class Autor {
    private Integer idautor;
    private String name;

    public Autor(Integer idautor, String name) {
        this.idautor = idautor;
        this.name = name;
    }

    public Autor() {

    }

    public Integer getId() {
        return idautor;
    }

    public void setId(int id) {
        this.idautor = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
            return "Autor - id=" + idautor + "name=" + name;
    }
}