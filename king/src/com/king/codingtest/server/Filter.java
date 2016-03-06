package com.king.codingtest.server;

import com.king.codingtest.utils.StringUtils;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Odyss on 19/06/2014.
 */
final class Filter extends com.sun.net.httpserver.Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        Command command = null;
        switch(httpExchange.getRequestMethod()) {
            case RequestMethod.GET: {
                command = parseGet(httpExchange);
                break;
            } case RequestMethod.POST: {
                command = parsePost(httpExchange,
                        parseGet(httpExchange));
                break;
            }
        }
        httpExchange.setAttribute("command", command);
        chain.doFilter(httpExchange);
    }

    @Override
    public String description() {
        return null;
    }

    private Command<?> parseGet(final HttpExchange httpExchange) {
        String path = httpExchange.getRequestURI().getPath().toLowerCase();
        String [] tokens = path.substring(1).split("[/\\?]");
        if(tokens.length < 2) {
            return Command.invalidCommand("Uri query should minimally contain a /<var>/action pattern");
        } else {
            Integer value;
            try {
                value = StringUtils.convertToUnsignedInteger(tokens[0]);
            } catch(NumberFormatException e) {
                return Command.invalidCommand(
                        String.format("Uri query variable should be of type unsigned integer: %s",
                                e.getLocalizedMessage()));
            }

            Command.Action action;
            try {
                action = Command.Action.valueOf(tokens[1].toUpperCase());
            } catch(IllegalArgumentException e) {
                return Command.invalidCommand(
                        "Uri query contains unknown action, should be one of [login|score|highscorelist]");
            }

            Map<String, Object> parameters = parseParameters(httpExchange.getRequestURI().getQuery(), new HashMap<>());

            return new Command<>(
                    action,
                    value,
                    parameters);
        }
    }

    private Command<?> parsePost(final HttpExchange httpExchange, final Command<?> command) {
        try {
            return new Command<>(
                    command.getAction(),
                    command.getValue(),
                    parseParameters(
                        new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).readLine(),
                            command.getParameters()));
        } catch (IOException e) {
            return Command.invalidCommand(
                    String.format("Unable to read request body %s", e.getLocalizedMessage()));
        }
    }

    private Map<String, Object> parseParameters(final String query, final Map<String, Object> parameterMap) {
        Map<String, Object> map = new HashMap<>(parameterMap);
        if(!StringUtils.isEmpty(query)) {
            Arrays.asList(query.split("[&]")).stream()
                    .map(a -> a.split("[=]"))
                    .filter(a -> a.length == 2)
                    .collect(Collectors.toMap(a -> a[0], a -> a[1], (a, b) -> b, () -> map));
        }
        return map;
    }

    public static void main(String [] args) {
        new Filter().parseParameters("15=500&20=19&100=50&900", new HashMap()).forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
