package com.king.codingtest.domain;

import java.util.Comparator;

/**
 * Created by Odyss on 22/06/2014.
 */
public final class Score implements Comparable<Score> {

    public static Comparator<Score> highScoreComparator() {
        return Comparator.comparing(Score::getScore);
    }

    private final Integer level;
    private final Integer score;
    private final User user;
    private final long date;

    public Score(final Integer level, final Integer score, final User user) {
        this.level = level;
        this.score = score;
        this.user = user;
        date = System.nanoTime();
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    public long getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (date != score1.date) return false;
        if (level != null ? !level.equals(score1.level) : score1.level != null) return false;
        if (score != null ? !score.equals(score1.score) : score1.score != null) return false;
        if (user != null ? !user.equals(score1.user) : score1.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        return result;
    }

    @Override
    public int compareTo(Score score) {
        if(this.equals(score)) {
            return 0;
        } else {
            if (this.getLevel() > score.getLevel()) {
                return 1;
            } else if (this.getLevel() < score.getLevel()) {
                return -1;
            } else {
                if (this.getScore() > score.getScore()) {
                    return 1;
                } else if (this.getScore() < score.getScore()) {
                    return -1;
                } else {
                    if(this.getDate() > score.getDate()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "level=" + level +
                ", score=" + score +
                ", user=" + user +
                ", date=" + date +
                '}';
    }


}
