package com.king.codingtest.server;

import com.king.codingtest.utils.StringUtils;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Odyss on 18/06/2014.
 */
final class Server {

    private static final int PORT = 8081;
    private static final int INITAL_DELAY = 10;
    private static final int PERIOD = 10;

    private void start() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.setExecutor(Executors.newCachedThreadPool());
            server.createContext(StringUtils.FORWARD_SLASH, new Handler())
                    .getFilters().add(new Filter());
            server.start();

            SessionCleaner.getInstance().schedule(INITAL_DELAY, PERIOD);

            System.out.println(String.format("Webserver listening on port %d", PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String [] args) {
        new Server().start();
    }
}
