package com.king.codingtest.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Odyss on 21/06/2014.
 */
public final class SessionStore {

    private static final int TIME_OUT_MINUTES = 10;

    private static final SessionStore INSTANCE = new SessionStore();

    private final Map<String, Session> pool = new ConcurrentHashMap<>();

    private SessionStore() {
    }

    public static SessionStore getInstance() {
        return INSTANCE;
    }

    public int getSize() {
        return pool.size();
    }

    public void addSession(final Session session) {
        pool.put(session.getId(), session);
    }

    public Session getSession(final String id) {
        return pool.get(id);
    }

    public Optional<Session> getSessionValidated(final String id) {
        Session session = getSession(id);
        if(session != null) {
            if(sessionExpired(session)) {
                removeSession(id);
                return Optional.empty();
            } else {
                return Optional.of(session);
            }
        } else {
            return Optional.empty();
        }
    }

    public void removeSession(final String id) {
        pool.remove(id);
    }

    private boolean sessionExpired(Session session) {
        if(TimeUnit.MILLISECONDS.toMinutes(
                System.currentTimeMillis() - session.getCreationTime()) >= TIME_OUT_MINUTES) {
            return true;
        }

        return false;
    }

    public void cleanUpExpiredSessions() {
        pool.keySet().parallelStream().forEach(session -> getSessionValidated(session));
    }
}
