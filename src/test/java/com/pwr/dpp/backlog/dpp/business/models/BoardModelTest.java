package com.pwr.dpp.backlog.dpp.business.models;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BoardModelTest {

    @Mock
    BoardModel mockBoard;

    @Test
    void listTasksWithCategoryTest() {
        assertNotNull(mockBoard);
        mockBoard.getToDo();
        mockBoard.getClosed();
        mockBoard.getOpen();
        mockBoard.getDoing();
        verify(mockBoard, atLeastOnce()).getToDo();
        verify(mockBoard, atLeastOnce()).getClosed();
        verify(mockBoard, atLeastOnce()).getOpen();
        verify(mockBoard, atLeastOnce()).getDoing();
    }

    @Test
    void listTasksWithCategoryAndUserTest() {
        assertNotNull(mockBoard);
        mockBoard.getToDo("exampleAuthor");
        mockBoard.getClosed("exampleAuthor");
        mockBoard.getOpen("exampleAuthor");
        mockBoard.getDoing("exampleAuthor");
        verify(mockBoard, atLeastOnce()).getToDo(anyString());
        verify(mockBoard, atLeastOnce()).getClosed(anyString());
        verify(mockBoard, atLeastOnce()).getOpen(anyString());
        verify(mockBoard, atLeastOnce()).getDoing(anyString());
    }

    @Test
    void listCommentsForTaskTest() {
        assertNotNull(mockBoard);
        for (int i = 0; i < 10; i++) {
            mockBoard.getCommentsForTask(i);
        }
        verify(mockBoard,atMost(10)).getCommentsForTask(anyInt());
    }

    @Test
    void listCommentsForUserTest() {
        assertNotNull(mockBoard);
        mockBoard.getCommentsForUser("exampleAuthor");
        verify(mockBoard, atLeastOnce()).getCommentsForUser(anyString());
    }
}
