package com.example.newsapp;

public class News {
    int id;
    String title, content;

    public News(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
