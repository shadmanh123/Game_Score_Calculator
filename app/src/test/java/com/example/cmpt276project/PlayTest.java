package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;
import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Play;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PlayTest {

    Game testGame1;
    Game testGame2;
    Game testGame3;

    @BeforeEach
    public void runBefore() {
        testGame1 = new Game("Chutes and Ladders", 30, 100);
        testGame2 = new Game("Poker", 100, 200);
        testGame3 = new Game("Some Card Game", 0, 10);
    }

    @Test
    public void testSimpleAchievement() {
        Play play1 = new Play(testGame1, 2, 150);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement);
    }

    @Test
    public void testBelowMinAchievement() {
        Play play1 = new Play(testGame1, 2, 10);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement);
    }


    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement);

    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Bottlenose Dolphin", achievement);
    }

}