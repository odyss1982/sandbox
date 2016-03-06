package com.king.codingtest;

import com.king.codingtest.controller.impl.HighScoreListController;
import com.king.codingtest.domain.Score;
import com.king.codingtest.domain.ScoreStore;
import com.king.codingtest.domain.User;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Odyss on 23/06/2014.
 */
public class HighScoreListTest {

    @Test
    public void testDomainGetHighScores() {
        Score score1 = new Score(1, 100, new User(14));
        ScoreStore.getInstance().addScore(score1);
        Score score2 = new Score(1, 100, new User(15));
        ScoreStore.getInstance().addScore(score2);
        Score score3 = new Score(1, 200, new User(15));
        ScoreStore.getInstance().addScore(score3);
        Score score4 = new Score(1, 120, new User(16));
        ScoreStore.getInstance().addScore(score4);
        Score score5 = new Score(1, 120, new User(16));
        ScoreStore.getInstance().addScore(score5);
        Score score6 = new Score(1, 500, new User(16));
        ScoreStore.getInstance().addScore(score6);
        Score score7 = new Score(1, 900, new User(16));
        ScoreStore.getInstance().addScore(score7);
        Score score8 = new Score(1, 800, new User(17));
        ScoreStore.getInstance().addScore(score8);
        Score score11 = new Score(3, 100, new User(15));
        ScoreStore.getInstance().addScore(score11);
        Score score9 = new Score(2, 100, new User(15));
        ScoreStore.getInstance().addScore(score9);
        Score score10 = new Score(2, 100, new User(15));
        ScoreStore.getInstance().addScore(score10);

        List<Score> list1 = ScoreStore.getInstance().getHighScoresDescending(0, 10);
        assertThat(list1, is(Arrays.<Score>asList()));

        List<Score> list2 = ScoreStore.getInstance().getHighScoresAscending(1, 2);
        assertThat(list2, is(Arrays.asList(score1, score2)));

        List<Score> list3 = ScoreStore.getInstance().getHighScoresDescending(1, 2);
        assertThat(list3, is(Arrays.asList(score7, score8)));

        ScoreStore.getInstance().empty();
    }

    @Test
    public void testControllerGetHighScoresDescending() {
        ScoreStore.getInstance().addScore(new Score(1, 900, new User(15)));
        ScoreStore.getInstance().addScore(new Score(1, 500, new User(20)));

        Response response = new HighScoreListController().executeCommand(new Command<>(Command.Action.HIGHSCORELIST, 1));
        assertEquals("15=900,20=500", response.getResponse());

        ScoreStore.getInstance().empty();
    }

}
