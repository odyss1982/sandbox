package com.king.codingtest.server;

import com.king.codingtest.controller.impl.HighScoreListController;
import com.king.codingtest.controller.impl.LoginController;
import com.king.codingtest.controller.impl.ScoreController;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * Created by Odyss on 18/06/2014.
 */
final class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Command<?> command = (Command)httpExchange.getAttribute("command");

        switch (command.getAction()) {
            case INVALID: {
                sendResponse(httpExchange,
                        handleInvalidCommand(command));
                break;
            } case LOGIN: {
                sendResponse(httpExchange,
                        handleLogin(command));
                break;
            } case SCORE: {
                httpExchange.sendResponseHeaders(
                        handleScore(command).getStatusCode().getCode(), 0);
                break;
            } case HIGHSCORELIST: {
                Headers headers = httpExchange.getResponseHeaders();
                headers.add("Content-Type", "text/csv");
                headers.add("Content-Disposition", "attachment;filename=higscores.csv");

                sendResponse(httpExchange,
                        handleHighScoreList(command));
                break;
            }
        }
    }

    private Response handleInvalidCommand(final Command<?> command) {
        return Response.failedRequest(StatusCode.BAD_REQUEST, command.getValue().toString());
    }

    private Response handleLogin(final Command<?> command) {
        return new LoginController().executeCommand(command.<Integer>reified());
    }

    private Response handleScore(final Command<?> command) {
        return new ScoreController().executeCommand(command.<Integer>reified());
    }

    private Response handleHighScoreList(final Command<?> command) {
        return new HighScoreListController().executeCommand(command.<Integer>reified());
    }

    private void sendResponse(final HttpExchange httpExchange, Response response) throws IOException {
        if(response.getStatusCode().equals(StatusCode.SUCCESS)) {
            writeResponse(httpExchange, response, () -> response.getResponse());
        } else {
            writeResponse(httpExchange, response, () -> response.getErrorMessage());
        }
    }

    private void writeResponse(final HttpExchange httpExchange, final Response response, final Supplier<String> answer) throws IOException {
        httpExchange.sendResponseHeaders(response.getStatusCode().getCode(),
                answer.get().length());
        OutputStream stream = httpExchange.getResponseBody();
        stream.write(answer.get().getBytes());
        stream.close();
    }
}
