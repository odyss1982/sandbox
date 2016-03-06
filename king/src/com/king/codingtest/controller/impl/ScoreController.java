package com.king.codingtest.controller.impl;

import com.king.codingtest.controller.Controller;
import com.king.codingtest.domain.Score;
import com.king.codingtest.domain.ScoreStore;
import com.king.codingtest.domain.Session;
import com.king.codingtest.domain.SessionStore;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;
import com.king.codingtest.server.StatusCode;
import com.king.codingtest.utils.StringUtils;

/**
 * Created by Odyss on 22/06/2014.
 */
public class ScoreController implements Controller<Integer> {

    @Override
    public Response executeCommand(final Command<Integer> command) {
        Session session;
        if(!command.getParameters().containsKey("session")) {
            return Response.failedRequest(StatusCode.BAD_REQUEST, "Missing session information");
        }
        else {
            session = SessionStore.getInstance().getSessionValidated(
                    (String) command.getParameters().get("session")).orElse(null);
            if(session == null) {
                return Response.failedRequest(StatusCode.SESSION_EXPIRED, "Session expired, please login");
            }
        }

        Integer score;
        if(!command.getParameters().containsKey("score")) {
            return Response.failedRequest(StatusCode.BAD_REQUEST, "Missing score information");
        } else {
            try {
                score = StringUtils.convertToUnsignedInteger((String)command.getParameters().get("score"));
            } catch(NumberFormatException e) {
                return Response.failedRequest(StatusCode.BAD_REQUEST, "Score is not an unsigned integer");
            }
        }

        ScoreStore.getInstance().addScore(
                new Score(command.getValue(),
                          score,
                          session.getUser()));

        return Response.successPostRequest();
    }
}
