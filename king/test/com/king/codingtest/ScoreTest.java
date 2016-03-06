package com.king.codingtest;

import com.king.codingtest.controller.impl.ScoreController;
import com.king.codingtest.domain.Session;
import com.king.codingtest.domain.SessionStore;
import com.king.codingtest.domain.User;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;
import com.king.codingtest.utils.StringUtils;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Odyss on 24/06/2014.
 */
public class ScoreTest {

    @Test
    public void testPostScoreWithoutSession() {
        Command<Integer> command = new Command<>(Command.Action.SCORE, 1,
                new HashMap<String, Object>(){{
                    put("score", "500");}}
        );

        Response response = new ScoreController().executeCommand(command);
        assertNotNull(response);
        assertFalse(response.getResult().isPresent());
        assertTrue(StringUtils.isEmpty(response.getResponse()));
        assertFalse(StringUtils.isEmpty(response.getErrorMessage()));
    }

    @Test
    public void testPostScoreWithoutScore() {
        Command<Integer> command = new Command<>(Command.Action.SCORE, 1,
                new HashMap<String, Object>(){{
                    put("session", "abc");}}
        );

        SessionStore.getInstance().addSession(new Session(new User(15), "abc"));
        Response response = new ScoreController().executeCommand(command);
        assertFalse(response.getResult().isPresent());
        assertTrue(StringUtils.isEmpty(response.getResponse()));
        assertFalse(StringUtils.isEmpty(response.getErrorMessage()));
    }

    @Test
    public void testPostScore() {
        Command<Integer> command = new Command<>(Command.Action.SCORE, 1,
                new HashMap<String, Object>(){{
                    put("session", "abc");
                    put("score", "500");}}
        );

        SessionStore.getInstance().addSession(new Session(new User(15), "abc"));
        Response response = new ScoreController().executeCommand(command);
        assertNotNull(response);
        assertTrue(StringUtils.isEmpty(response.getErrorMessage()));
    }
}
