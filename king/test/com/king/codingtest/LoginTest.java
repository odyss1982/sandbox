package com.king.codingtest;

import com.king.codingtest.controller.impl.LoginController;
import com.king.codingtest.domain.Session;
import com.king.codingtest.domain.User;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Odyss on 24/06/2014.
 */
public class LoginTest {

    @Test
    public void loginDomainTest() {
        assertTrue(new Session(new User(15)).getId().matches("[A-Za-z0-9]+"));
    }

    @Test
    public void loginControllerTest() {
        Response response = new LoginController().executeCommand(new Command<>(Command.Action.LOGIN, 15));
        assertNotNull(response);
        assertNotNull(response.getResult());
    }
}
