package com.king.codingtest.server;

import com.king.codingtest.utils.StringUtils;

import java.util.Optional;

/**
 * Created by Odyss on 22/06/2014.
 */
public class Response {

    public static Response failedRequest(StatusCode statusCode, final String message) {
        return new Response(statusCode, message);
    }

    public static Response successRequest(Object result, String response) {
        return new Response(result, response);
    }

    public static Response successPostRequest() {
        return new Response(StatusCode.SUCCESS, null, "");
    }

    private final Optional<Object> result;
    private final StatusCode statusCode;
    private final String errorMessage;
    private final String response;

    public Response(final Object result, String response) {
        this(StatusCode.SUCCESS, result, response);
    }

    public Response(final StatusCode resultCode, final Object result , final String response) {
        this.result = Optional.ofNullable(result);
        this.statusCode = resultCode;
        this.errorMessage = "";
        this.response = response;
    }

    public Response(final StatusCode statusCode, final String errorMessage) {
        this.result = Optional.empty();
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.response = "";
    }

    public Optional<Object> getResult() {
        return result;
    }

    public String getResponse() {
        return response;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return getStatusCode() + " " + getErrorMessage()
                + StringUtils.LINE_SEPERATOR
                + "Result: " + getResult().toString();
    }
}
