package com.king.codingtest.controller.impl;

import com.king.codingtest.controller.Controller;
import com.king.codingtest.domain.Session;
import com.king.codingtest.domain.SessionStore;
import com.king.codingtest.domain.User;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;

/**
 * Created by Odyss on 21/06/2014.
 */
public class LoginController implements Controller<Integer> {

    @Override
    public Response executeCommand(final Command<Integer> command) {
        SessionStore pool = SessionStore.getInstance();
        Session session = new Session(
                new User(command.<Integer>getValue()));
        pool.addSession(session);
        return Response.successRequest(session, session.getId());
    }
}
