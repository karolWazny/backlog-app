package com.pwr.dpp.backlog.dpp.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Comment;
import com.pwr.dpp.backlog.dpp.business.orm.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class JsonDatabaseHandler implements DatabaseHandler{
    private Path commentsPath;
    private Path tasksPath;
    private Path usersPath;
    private ObjectMapper objectMapper;

    private List<Task> tasks;
    private List<Comment> comments;
    private List<String> users;

    public JsonDatabaseHandler(String databaseDirectoryPath, String tasksFilename, String commentsFilename, String usersFilename){
        objectMapper = new ObjectMapper();
        Path databaseDirPath = Paths.get(databaseDirectoryPath);
        if(Files.notExists(databaseDirPath)) {
            try {
                tasks = new ArrayList<>();
                comments = new ArrayList<>();
                users = new ArrayList<>();
                Files.createDirectory(databaseDirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tasksPath = Paths.get(databaseDirectoryPath + File.separator + (tasksFilename.strip().contains(".json") ? tasksFilename.strip() : tasksFilename.strip() + ".json"));
        commentsPath = Paths.get( databaseDirectoryPath + File.separator + (commentsFilename.strip().contains(".json") ? commentsFilename.strip() : commentsFilename.strip() + ".json"));
        usersPath = Paths.get( databaseDirectoryPath + File.separator + (usersFilename.strip().contains(".json") ? usersFilename.strip() : usersFilename.strip() + ".json"));
        if(Files.exists(tasksPath)) {
            tasks = readTasks();
        }else{
            tasks = new ArrayList<>();
        }
        if(Files.exists(commentsPath)) {
            comments = readComments();
        }else{
            comments = new ArrayList<>();
        }
        if(Files.exists(usersPath)){
            users = readUsers();
        }else{
            users = new ArrayList<>();
        }

    }

    public static void main(String[] args) {
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");


    }

    private void saveDefaultDataBase(){
        Random random = new Random(System.currentTimeMillis());
        Category[] categories = new Category[]{Category.DOING, Category.CLOSED, Category.OPEN, Category.TODO};
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");
        List<Task> tasks = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new Task(i, "task" + i, "description1" + i, new Date(System.currentTimeMillis()), "user" + i, categories[random.nextInt(categories.length)]));
        }
        dh.saveTasks(tasks);

        List<Comment> comments = new LinkedList<>();
        List<String> users = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            comments.add(new Comment(i, tasks.get(random.nextInt(tasks.size())), new Date(System.currentTimeMillis()), "Comment number " + i, "author" +i));
            users.add("author" + i);
        }
        dh.saveComments(comments);
        dh.saveUsers(users);

    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }


    private List<Task> readTasks() {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class);
        try {
            return objectMapper.readValue(tasksPath.toFile(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveTask(Task task) {
        int existingTaskIndex = getTaskIndex(task.getId());
        if(existingTaskIndex >= 0){
            tasks.set(existingTaskIndex, task);
        }else{
            tasks.add(task);
        }
    }
    private int getTaskIndex(Integer id){
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (Objects.equals(t.getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteTask(Task task) {
        int existingTaskIndex = getTaskIndex(task.getId());
        if(existingTaskIndex >= 0){
            tasks.remove(existingTaskIndex);
        }
    }

    @Override
    public void saveTasks(List<Task> tasks) {
        try {
            objectMapper.writeValue(tasksPath.toFile(), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Comment> getCommentsForTask(Integer taskId) {
        List<Comment> relatedComments = new LinkedList<>();
        for(Comment c : comments){
            if(c.getTask().getId().equals(taskId)){
                relatedComments.add(c);
            }
        }
        return relatedComments;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    private List<Comment> readComments(){
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Comment.class);
        try {
            return objectMapper.readValue(commentsPath.toFile(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveComment(Comment comment) {
        int existingCommentIndex = getCommentIndex(comment.getId());
        if(existingCommentIndex >= 0){
            comments.set(existingCommentIndex, comment);
        }else{
            comments.add(comment);
        }
    }

    private int getCommentIndex(Integer id){
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            if (Objects.equals(c.getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void saveComments(List<Comment> comments) {
        try {
            objectMapper.writeValue(commentsPath.toFile(), comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getUsers() {
        return users;
    }

    private void saveUsers(List<String> users) {
        try {
            objectMapper.writeValue(usersPath.toFile(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readUsers(){
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
        try {
            return objectMapper.readValue(usersPath.toFile(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Creates new user with given username unless user with given username exists.
     * @param username
     * @return True if new user was created, false otherwise.
     */
    @Override
    public boolean createUser(String username) {
        try {
            if(!logAs(username)){
                users.add(username);
                return true;
            }else{
                return false;
            }
        } catch (NoSuchUserException e) {
            return false;
        }
    }

    /**
     * Checks if user with given username exists.
     * @param username
     * @return True if user exists, false otherwise.
     * @throws NoSuchUserException when user doesn't exist
     */
    @Override
    public boolean logAs(String username) throws NoSuchUserException {
        for(String user:users){
            if(user.equalsIgnoreCase(username)){
                return true;
            }
        }
        throw new NoSuchUserException();
    }
}
