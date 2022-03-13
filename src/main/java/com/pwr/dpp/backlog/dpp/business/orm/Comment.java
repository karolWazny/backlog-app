package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDate;

public class Comment {
    private Integer id;
    private Task task;
    private LocalDate dateCreated;
    private String content;
    private String author;
}
