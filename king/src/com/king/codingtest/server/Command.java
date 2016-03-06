package com.king.codingtest.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Odyss on 19/06/2014.
 */
public final class Command<T> {

    public enum Action {LOGIN, SCORE, HIGHSCORELIST, INVALID}

    final static Command invalidCommand(final String error) {
        return new Command<>(Action.INVALID, error);
    }

    private final Action action;
    private final T value;
    private final Map<String, Object> parameters;

    public Command(final Action action, final T value) {
        this(action, value, new HashMap<String, Object>());
    }

    public Command(final Action action, final T value, final Map<String, Object> parameters) {
        this.action = action;
        this.value = value;
        this.parameters = parameters;
    }

    public Action getAction() {
        return action;
    }

    public T getValue() {
        return value;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public boolean isInvalid() {
        return Action.INVALID.equals(this.getAction());
    }

    public <T> Command<T> reified() {
        return (Command<T>) this;
    }
}
