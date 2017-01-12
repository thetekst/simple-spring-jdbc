package com.dmitry.tkachenko.spring.jdbc.dao.objects;

/**
 * Created by dmitry tkachenko on 1/11/17.
 */
public class Mp3 {
    private int id;
    private String name;
    private String author;

    public Mp3() {
    }

    public Mp3(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Mp3(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
