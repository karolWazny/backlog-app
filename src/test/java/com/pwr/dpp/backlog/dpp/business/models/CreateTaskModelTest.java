package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import junit.framework.TestCase;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class CreateTaskModelTest extends TestCase {
    @Test
    public void createTaskFullData(@Mocked DatabaseHandler mock){

        CreateTaskInfo info = new CreateTaskInfo();
        info.setUsername("username");
        info.setDescription("description");
        info.setCategory(Category.OPEN);
        info.setTitle("some title");

        CreateTaskModel tested = new CreateTaskModel();
        tested.setDatabaseHandler(mock);
        tested.createTask(info);

        new Verifications(){{
            Task task;
            mock.saveTask(task = withCapture());
            Assertions.assertEquals("description", task.getDescription());
            Assertions.assertEquals(Category.OPEN, task.getCategory());
            Assertions.assertEquals("username", task.getUser());
            Assertions.assertEquals("some title", task.getName());
        }};
    }
    @Test
    public void createTaskMinimalData(@Mocked DatabaseHandler mock){

        CreateTaskInfo info = new CreateTaskInfo();
        info.setTitle("some title");

        CreateTaskModel tested = new CreateTaskModel();
        tested.setDatabaseHandler(mock);
        tested.createTask(info);

        new Verifications(){{
            Task task;
            mock.saveTask(task = withCapture());
            Assertions.assertEquals("", task.getDescription());
            Assertions.assertEquals(Category.OPEN, task.getCategory());
            Assertions.assertTrue(task.getUser().equalsIgnoreCase("unassigned"));
            Assertions.assertEquals("some title", task.getName());
        }};
    }
    @Test
    public void createTaskNullName(@Mocked DatabaseHandler mock){
        try{
            CreateTaskInfo info = new CreateTaskInfo();

            CreateTaskModel tested = new CreateTaskModel();
            tested.setDatabaseHandler(mock);
            tested.createTask(info);
            fail();
        } catch(NullPointerException ignored){
        }
    }
    @Test
    public void createTaskBlankName(@Mocked DatabaseHandler mock){
        try{
            CreateTaskInfo info = new CreateTaskInfo();
            info.setTitle("  \n \t   ");

            CreateTaskModel tested = new CreateTaskModel();
            tested.setDatabaseHandler(mock);
            tested.createTask(info);
            fail();
        } catch(RuntimeException ignored){
        }
    }
}
