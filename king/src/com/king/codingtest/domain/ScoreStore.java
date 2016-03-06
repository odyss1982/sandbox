package com.king.codingtest.domain;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Created by Odyss on 22/06/2014.
 * Thread safe singleton
 */
public final class ScoreStore {

    private static ScoreStore INSTANCE = new ScoreStore();

    private ScoreStore(){}

    public static ScoreStore getInstance() {
        return INSTANCE;
    }

    private final Map<Integer, Map<User, Set<Score>>> scores = new ConcurrentHashMap<>();

    public void addScore(final Score score) {
        if(!scores.containsKey(score.getLevel())) {
            scores.put(score.getLevel(), new ConcurrentHashMap<>());
        }
        if(!scores.get(score.getLevel()).containsKey(score.getUser())) {
            scores.get(score.getLevel()).put(score.getUser(), new ConcurrentSkipListSet<>());
        }
        scores.get(score.getLevel()).get(score.getUser()).add(score);
    }

    /**
     * Get high scores the java 8 way: concurrent and thread-safe due to its functional stateless nature
     */
    public List<Score> getHighScoresAscending(int level, int limit) {
        if (scores.get(level) == null) {
            return new ArrayList<>();
        }

        return scores.get(level).values().stream().parallel()
                .map(a -> a.stream().findFirst().get())
                .sorted(Score.highScoreComparator())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Score> getHighScoresDescending(int level, int limit) {
        if(scores.get(level) == null) {
            return new ArrayList<>();
        }

        return scores.get(level).values().stream().parallel()
                .map(a -> a.stream().max(Score.highScoreComparator()).get())
                .sorted(Score.highScoreComparator().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void empty() {
        scores.clear();
    }

    public static void main(String [] args) {
        ScoreStore.getInstance().addScore(new Score(1, 100, new User(14)));
        ScoreStore.getInstance().addScore(new Score(1, 100, new User(15)));
        ScoreStore.getInstance().addScore(new Score(1, 200, new User(15)));
        ScoreStore.getInstance().addScore(new Score(1, 120, new User(16)));
        ScoreStore.getInstance().addScore(new Score(1, 120, new User(16)));
        ScoreStore.getInstance().addScore(new Score(1, 500, new User(16)));
        ScoreStore.getInstance().addScore(new Score(1, 900, new User(16)));
        ScoreStore.getInstance().addScore(new Score(1, 800, new User(17)));
        ScoreStore.getInstance().addScore(new Score(2, 100, new User(15)));
        ScoreStore.getInstance().addScore(new Score(2, 100, new User(15)));
        ScoreStore.getInstance().addScore(new Score(3, 100, new User(15)));

        ScoreStore.getInstance().getHighScoresDescending(1, 2).forEach(
                score -> System.out.println(score.getUser().getId() + ": " + score.getScore()));
    }
}
