package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.orm.Category;
import com.pwr.dpp.backlog.dpp.business.orm.Task;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class CreateTaskModelTest {
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
}
