package com.king.codingtest.controller;

import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;

/**
 * Created by Odyss on 21/06/2014.
 */
public interface Controller<T> {

    public Response executeCommand(final Command<T> command);
}
