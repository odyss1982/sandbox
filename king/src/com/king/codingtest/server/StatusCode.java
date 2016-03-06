package com.king.codingtest.server;

/**
 * Created by Odyss on 22/06/2014.
 */
public enum StatusCode {

    SUCCESS(200), BAD_REQUEST(400), SESSION_EXPIRED(440);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
