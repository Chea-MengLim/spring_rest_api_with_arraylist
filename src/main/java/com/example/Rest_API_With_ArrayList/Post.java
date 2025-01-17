package com.example.Rest_API_With_ArrayList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {

    // fields
    private int id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime creationDate;
    private List<String>  tags = new ArrayList<>();

    // constructor
    public Post(int id, String title, String content, String author, LocalDateTime creationDate, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.tags = tags;
    }
    public Post(){}

    // setter getter method

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


}

