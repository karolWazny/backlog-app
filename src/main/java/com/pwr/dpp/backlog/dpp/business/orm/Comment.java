package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDate;

public class Comment {
    private Integer id;
    private Task task;
    private LocalDate dateCreated;
    private String content;
    private String author;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Task getTask() {return task;}

    public void setTask(Task task) {this.task = task;}

    public LocalDate getDateCreated() {return dateCreated;}

    public void setDateCreated(LocalDate dateCreated) {this.dateCreated = dateCreated;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}
}
