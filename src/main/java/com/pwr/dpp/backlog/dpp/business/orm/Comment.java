package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Comment implements Comparable<Comment>{
    private Integer id;
    private Task task;
    private Date timeCreated;
    private String content;
    private String author;

    public Comment(Integer id, Task task, Date timeCreated, String content, String author) {
        this.id = id;
        this.task = task;
        this.timeCreated = timeCreated;
        this.content = content;
        this.author = author;
    }

    public Comment() {
        timeCreated = new Date(System.currentTimeMillis());
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = Objects.requireNonNull(task);
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
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
