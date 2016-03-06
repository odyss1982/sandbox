package com.king.codingtest.controller.impl;

import com.king.codingtest.controller.Controller;
import com.king.codingtest.domain.Score;
import com.king.codingtest.domain.ScoreStore;
import com.king.codingtest.server.Command;
import com.king.codingtest.server.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Odyss on 22/06/2014.
 */
public class HighScoreListController implements Controller<Integer> {

    @Override
    public Response executeCommand(Command<Integer> command) {

        List<Score> scores = ScoreStore.getInstance().getHighScoresDescending(command.getValue(), 15);
        String response = scores.stream()
                .map(score -> score.getUser().getId() + "=" + score.getScore())
                .collect(Collectors.joining(","));

        return Response.successRequest(scores, response);
    }
}
