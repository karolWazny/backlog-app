package com.pwr.dpp.backlog.dpp.business.orm;

public enum Category {
    OPEN("Open"),
    TODO("To Do"),
    DOING("Doing"),
    CLOSED("Closed");
    public final String name;
    Category(String name){
        this.name = name;
    }
}
