package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment implements Comparable<Comment>{
    private Integer id;
    private Task task;
    private LocalDateTime timeCreated;
    private String content;
    private String author;

    public Comment() {
        timeCreated = LocalDateTime.now();
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = Objects.requireNonNull(task);
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
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

    @Override
    public int compareTo(Comment o) {
        return this.timeCreated.compareTo(o.timeCreated);
    }
}
