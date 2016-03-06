package com.king.codingtest.domain;

import java.util.UUID;

/**
 * Created by Odyss on 21/06/2014.
 */
public final class Session {

    private final String id;
    private final User user;
    private final long creationTime;

    public Session(final User user) {
        this(user, UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public Session(final User user, final String sessionId) {
        this.id = sessionId;
        this.user = user;
        creationTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", user=" + user +
                ", creationTime=" + creationTime +
                '}';
    }
}
