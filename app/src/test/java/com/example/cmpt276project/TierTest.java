package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Land;
import com.example.cmpt276project.model.Ocean;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.Sky;
import com.example.cmpt276project.model.Tier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TierTest {

    Game testGame1;
    Game testGame2;
    Game testGame3;
    Tier testOcean;

    @BeforeEach
    public void runBefore() {
        testGame1 = new Game("Chutes and Ladders", 30, 100);
        testGame2 = new Game("Poker", 100, 200);
        testGame3 = new Game("Some Card Game", 0, 10);
        testOcean = Ocean.LEVEL1;
    }

    @Test
    public void testDifferentTierTypes() {
        List<Integer> scores = new ArrayList<>();
        scores.add(20);
        scores.add(40);
        scores.add(90);
        Play play = new Play(testGame1, 3, scores, testOcean);
        assertEquals("OCEAN", testOcean.getClassName());
        System.out.println(testOcean.getClassName());
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        Tier testLand = Land.LEVEL1;
        play = new Play(testGame1, 3, scores, testLand);
        assertEquals("LAND", testLand.getClassName());
        play.getListOfAchievements();
        achievement = play.getAchievementScore();
        assertEquals("Chimpanzee", achievement);

        Tier testSky = Sky.LEVEL1;
        play = new Play(testGame1, 3, scores, testSky);
        assertEquals("SKY", testSky.getClassName());
        play.getListOfAchievements();
        achievement = play.getAchievementScore();
        assertEquals("Bald Eagle", achievement);
        testGame3 = new Game("Some Card Game", 0, 7);
    }
}
