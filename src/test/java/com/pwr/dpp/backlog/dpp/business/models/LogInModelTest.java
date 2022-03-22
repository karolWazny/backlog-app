package com.pwr.dpp.backlog.dpp.business.models;

import com.pwr.dpp.backlog.dpp.business.DatabaseHandler;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepository;
import com.pwr.dpp.backlog.dpp.business.LoggedUserRepositoryImpl;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class LogInModelTest {

    @Injectable
    DatabaseHandler databaseHandler;

    LoggedUserRepository loggedUserRepository;

    @Test
    public void succesfullLogin(){
        new Expectations(){{
            databaseHandler.logAs("username"); returns(Boolean.TRUE, null);
        }};
        loggedUserRepository = new LoggedUserRepositoryImpl();
        LogInModel model = new LogInModel();
        model.setLoggedUserRepository(loggedUserRepository);
        model.setDatabaseHandler(databaseHandler);
        Assertions.assertTrue(model.logAs("username"));
        Assertions.assertEquals("username", loggedUserRepository.getLoggedUser());
    }

    @Test
    public void failedLogin(){
        new Expectations(){{
            databaseHandler.logAs("username"); returns(Boolean.FALSE, null);
        }};
        loggedUserRepository = new LoggedUserRepositoryImpl();
        LogInModel model = new LogInModel();
        model.setLoggedUserRepository(loggedUserRepository);
        model.setDatabaseHandler(databaseHandler);
        Assertions.assertFalse(model.logAs("username"));
        Assertions.assertNull(loggedUserRepository.getLoggedUser());
    }
}
