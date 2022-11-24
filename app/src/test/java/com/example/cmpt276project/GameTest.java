package com.example.cmpt276project;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Options;
import com.example.cmpt276project.model.tiers.Land;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Sky;
import com.example.cmpt276project.model.tiers.Tier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameTest {
    Game game;
    List<Double> scores;

    @BeforeEach
    public void runBefore() {
        game = new Game("Chutes and Ladders", 0, 100);
        /*Options option = new Options("easy", Ocean.LEVEL1);
        generatePlays(option);

        scores.clear();
        option = new Options("easy", Land.LEVEL1);
        generatePlays(option);

        scores.clear();
        option = new Options("easy", Sky.LEVEL1);
        generatePlays(option);*/

        scores = new ArrayList<>();
        Options option = new Options("normal", Ocean.LEVEL1);
        generatePlays(game, scores, option);

        scores.clear();
        Options option1 = new Options("normal", Land.LEVEL1);
        generatePlays(game, scores, option1);

        scores.clear();
        Options option2 = new Options("normal", Sky.LEVEL1);
        generatePlays(game, scores, option2);

        /*scores.clear();
        option = new Options("hard", Ocean.LEVEL1);
        generatePlays(option);

        scores.clear();
        option = new Options("hard", Land.LEVEL1);
        generatePlays(option);

        scores.clear();
        option = new Options("hard", Sky.LEVEL1);
        generatePlays(option);*/
    }

    private void generatePlays(Game game, List<Double> scores, Options option) {
        for (int i = 0; i < 10; i++) {
            scores.add(10.0);
            Play play = new Play(game, 2, scores, option);
            game.addPlay(play);
        }
    }

    @Test
    public void test() {
        game.achievementStatistics();
    }
}
