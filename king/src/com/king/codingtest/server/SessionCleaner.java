package com.king.codingtest.server;

import com.king.codingtest.domain.SessionStore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Odyss on 25/06/2014.
 */
public final class SessionCleaner {

    private static final SessionCleaner INSTANCE = new SessionCleaner();

    private SessionCleaner() {
    }

    public static SessionCleaner getInstance() {
        return INSTANCE;
    }

    private final Runnable cleaner = new Runnable() {
        @Override
        public void run() {
            SessionStore.getInstance().cleanUpExpiredSessions();
        }
    };

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void schedule(int initalDelay, int period) {
        scheduler.scheduleAtFixedRate(cleaner, initalDelay, period, TimeUnit.SECONDS);
    }
}
