package com.example.demo.model;

import java.time.LocalDate;

public class Livro{
    private Integer idlivro;
    private String name;
    private LocalDate date;
    private Autor autor;
    private StatusLivro status;

    public Livro(Integer idlivro, String name, Autor autor, LocalDate date, StatusLivro status) {
        this.idlivro = idlivro;
        this.name = name;
        this.autor = autor;
        this.date = date;
        this.status = status;
    }

    public Livro() {

    }

    public Integer getId() {
        return idlivro;
    }

    public void setId(Integer id) {
        this.idlivro = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + idlivro + '\'' +
                ", name='" + name + '\'' +
                ", autor=" + autor +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}