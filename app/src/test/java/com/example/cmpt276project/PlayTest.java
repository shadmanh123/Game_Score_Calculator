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
        assertEquals("Colossal Squid", achievement);

        Play play2 = new Play(testGame2, 2, 150);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Flamingo Tongue Snail", achievement2);
    }

    @Test
    public void testBelowMinAchievement() {
        Play play1 = new Play(testGame1, 2, 10);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 50);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement2);
    }


    @Test
    public void testMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 200);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 400);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement2);
    }

    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 450);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Blue Whale", achievement2);

    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 0);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement2);
    }

    @Test
    public void testBelowMin1Achievement() {
        Play play1 = new Play(testGame1, 2, 16);
        play1.achievementsAndScores();
        String achievement = play1.calculateAchievementForGroupScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 57);
        play2.achievementsAndScores();
        String achievement2 = play2.calculateAchievementForGroupScore();
        assertEquals("Frogfish", achievement2);

        Play play3 = new Play(testGame3, 2, 3);
        play3.achievementsAndScores();
        String achievement3 = play3.calculateAchievementForGroupScore();
        assertEquals("Frogfish", achievement3);


    }


}