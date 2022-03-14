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


public class JsonDatabaseHandler implements DatabaseHandler {
    private Path commentsPath;
    private Path tasksPath;
    private Path usersPath;
    private ObjectMapper objectMapper;

    private List<Task> tasks;
    private List<Comment> comments;
    private List<String> users;

    public JsonDatabaseHandler(String databaseDirectoryPath, String tasksFilename, String commentsFilename, String usersFilename) {
        objectMapper = new ObjectMapper();
        Path databaseDirPath = Paths.get(databaseDirectoryPath);
        if (Files.notExists(databaseDirPath)) {
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
        commentsPath = Paths.get(databaseDirectoryPath + File.separator + (commentsFilename.strip().contains(".json") ? commentsFilename.strip() : commentsFilename.strip() + ".json"));
        usersPath = Paths.get(databaseDirectoryPath + File.separator + (usersFilename.strip().contains(".json") ? usersFilename.strip() : usersFilename.strip() + ".json"));
        load();
    }


    //TODO: remove this example main from JsonDatabaseHandler
    public static void main(String[] args) {
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");
        dh.saveDefaultDataBase();
//        List<Task> t = dh.getTasks();
//        dh.saveTask(new Task(2, "changed", "chaanged", new Date(System.currentTimeMillis()), "someuser", Category.CLOSED));
//        dh.saveTask(new Task(100, "changed2", "chaanged2", new Date(System.currentTimeMillis()), "someuser", Category.CLOSED));
//        dh.deleteTask(new Task(0, "changed", "chaanged", new Date(System.currentTimeMillis()), "someuser", Category.CLOSED));
//        dh.deleteTask(new Task(101, "changed", "chaanged", new Date(System.currentTimeMillis()), "someuser", Category.CLOSED));
//        List<Comment> cft = dh.getCommentsForTask(2);
//        List<Comment> c = dh.getComments();
//        List<String> u = dh.getUsers();
//        dh.saveComment(new Comment(1, t.get(0), new Date(System.currentTimeMillis()), "somecontent", "someauthorr"));
//        dh.saveComment(new Comment(100, t.get(0), new Date(System.currentTimeMillis()), "somecontent", "someauthorr"));
//        boolean a = dh.createUser("author5");
//        a = dh.createUser("authorxdddd");
//        a = dh.logAs("author5");
//        a = dh.logAs("author54");
//        dh.save();

    }

    private void saveDefaultDataBase() {
        Random random = new Random(System.currentTimeMillis());
        Category[] categories = new Category[]{Category.DOING, Category.CLOSED, Category.OPEN, Category.TODO};
        JsonDatabaseHandler dh = new JsonDatabaseHandler(System.getProperty("user.dir") + File.separator + "jsonDatabase", "tasks", "comments", "users");
        List<Task> tasks = new LinkedList<>();
        List<Comment> comments = new LinkedList<>();
        List<String> users = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new Task(i, "task" + i, "description" + i, new Date(System.currentTimeMillis()), "user" + i, categories[random.nextInt(categories.length)]));
        }
        for (int i = 0; i < 20; i++) {
            comments.add(new Comment(i, tasks.get(random.nextInt(tasks.size())), new Date(System.currentTimeMillis()), "Comment number " + i, "author" + i));
            users.add("author" + i);
        }
        dh.saveTasks(tasks);
        dh.saveComments(comments);
        dh.saveUsers(users);

    }

    /**
     * Saves tasks, comments and users to location data files given in {@link JsonDatabaseHandler} constructor.
     */
    @Override
    public void save() {
        this.saveTasks(tasks);
        this.saveComments(comments);
        this.saveUsers(users);
    }

    /**
     * Loads tasks, comments and users from memory if data files given in {@link JsonDatabaseHandler} constructor exist.
     */
    @Override
    public void load() {
        if (Files.exists(tasksPath)) {
            tasks = readTasks();
        } else {
            tasks = new ArrayList<>();
        }
        if (Files.exists(commentsPath)) {
            comments = readComments();
        } else {
            comments = new ArrayList<>();
        }
        if (Files.exists(usersPath)) {
            users = readUsers();
        } else {
            users = new ArrayList<>();
        }
    }

    /**
     * Returns loaded tasks.
     * @return {@link List} of tasks.
     */
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

    /**
     * Adds given {@link Task} to tasks {@link List}, if task with the same id exists, it overwrites existing task.
     *
     * @param task
     */
    @Override
    public void saveTask(Task task) {
        int existingTaskIndex = getTaskIndex(task.getId());
        if (existingTaskIndex >= 0) {
            tasks.set(existingTaskIndex, task);
        } else {
            if (tasks.size() > 0) {
                task.setId(tasks.get(tasks.size() - 1).getId() + 1);
            } else {
                task.setId(0);
            }
            tasks.add(task);
        }
    }

    private int getTaskIndex(Integer id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (Objects.equals(t.getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes given task from tasks {@link List} if task with the same id exists.
     *
     * @param task task to remove
     */
    @Override
    public void deleteTask(Task task) {
        int existingTaskIndex = getTaskIndex(task.getId());
        if (existingTaskIndex >= 0) {
            tasks.remove(existingTaskIndex);
        }
    }

    private void saveTasks(List<Task> tasks) {
        try {
            objectMapper.writeValue(tasksPath.toFile(), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns comments related to task with given task ID.
     *
     * @param taskId task ID.
     * @return comments related to task with given task ID.
     */
    @Override
    public List<Comment> getCommentsForTask(Integer taskId) {
        List<Comment> relatedComments = new LinkedList<>();
        for (Comment c : comments) {
            if (c.getTask().getId().equals(taskId)) {
                relatedComments.add(c);
            }
        }
        return relatedComments;
    }

    /**
     * Returns all loaded comments.
     *
     * @return all loaded comments.
     */
    @Override
    public List<Comment> getComments() {
        return comments;
    }

    private List<Comment> readComments() {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Comment.class);
        try {
            return objectMapper.readValue(commentsPath.toFile(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Adds given {@link Comment} to comments {@link List}, if comment with the same id exists, it overwrites existing commment.
     *
     * @param comment comment to add/update.
     */
    @Override
    public void saveComment(Comment comment) {
        int existingCommentIndex = getCommentIndex(comment.getId());
        if (existingCommentIndex >= 0) {
            comments.set(existingCommentIndex, comment);
        } else {
            if (comments.size() > 0) {
                comment.setId(comments.get(comments.size() - 1).getId() + 1);
            } else {
                comment.setId(0);
            }
            comments.add(comment);
        }
    }


    private int getCommentIndex(Integer id) {
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            if (Objects.equals(c.getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Saves given comments list to users file path given in {@link JsonDatabaseHandler} constructor.
     *
     * @param comments {@link List} containing comments to save.
     */
    public void saveComments(List<Comment> comments) {
        try {
            objectMapper.writeValue(commentsPath.toFile(), comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns stored users list.
     *
     * @return stored users list.
     */
    @Override
    public List<String> getUsers() {
        return users;
    }

    /**
     * Saves given users list to users file path given in {@link JsonDatabaseHandler} constructor.
     *
     * @param users
     */
    private void saveUsers(List<String> users) {
        try {
            objectMapper.writeValue(usersPath.toFile(), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads users from users file path given in {@link JsonDatabaseHandler} constructor.
     *
     * @return List of users or empty list if there is no users data file.
     */
    private List<String> readUsers() {
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
        try {
            return objectMapper.readValue(usersPath.toFile(), javaType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Creates new user with given username unless user with given username exists.
     *
     * @param username
     * @return True if new user was created, false otherwise.
     */
    @Override
    public boolean createUser(String username) {
        if (!logAs(username)) {
            users.add(username);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if user with given username exists.
     *
     * @param username
     * @return True if user exists, false otherwise.
     */
    @Override
    public boolean logAs(String username) {
        for (String user : users) {
            if (user.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
