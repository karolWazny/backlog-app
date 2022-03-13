package com.pwr.dpp.backlog.dpp.business.orm;

import java.time.LocalDate;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private LocalDate dateCreated;
    private String user;
    private Category category;
}
