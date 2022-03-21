package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private Date timeCreated;
    private String user;
    private Category category;

    public Task() {
    }

    public Task(Integer id, String name, String description, Date timeCreated, String user, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.user = user;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date dateCreated) {
        this.timeCreated = dateCreated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
