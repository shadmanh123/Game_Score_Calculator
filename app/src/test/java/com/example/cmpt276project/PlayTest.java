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
    public void testSimpleAchievementP1() {
        Play play1 = new Play(testGame1, 3, 150);
        play1.getAchievementsAndScores();
        String achievement = play1.getAchievements();
        assertEquals("Giant Pacific Octopus", achievement);

        Play play2 = new Play(testGame2, 5, 700);
        play2.getAchievementsAndScores();
        String achievement2 = play2.getAchievements();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        Play play3 = new Play(testGame3, 1, 3);
        play3.getAchievementsAndScores();
        String achievement3 = play3.getAchievements();
        assertEquals("Peacock Mantis Shrimp", achievement3);
    }


    @Test
    public void testMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 200);
        play1.getAchievementsAndScores();
        String achievement = play1.getAchievements();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 400);
        play2.getAchievementsAndScores();
        String achievement2 = play2.getAchievements();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 20);
        play3.getAchievementsAndScores();
        String achievement3 = play3.getAchievements();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250);
        play1.getAchievementsAndScores();
        String achievement = play1.getAchievements();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 450);
        play2.getAchievementsAndScores();
        String achievement2 = play2.getAchievements();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 25);
        play3.getAchievementsAndScores();
        String achievement3 = play3.getAchievements();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0);
        play1.getAchievementsAndScores();
        String achievement = play1.getAchievements();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 0);
        play2.getAchievementsAndScores();
        String achievement2 = play2.getAchievements();
        assertEquals("Blobfish", achievement2);

        Play play3 = new Play(testGame3, 2, 0);
        play3.getAchievementsAndScores();
        String achievement3 = play3.getAchievements();
        assertEquals("Blobfish", achievement3);
    }

    @Test
    public void testBelowMinAchievementP1() {
        Play play = new Play(testGame1, 2, 10);
        play.getAchievementsAndScores();
        String achievement = play.getAchievements();
        assertEquals("Blobfish", achievement);

        play.setTotalScore(16);
        achievement = play.getAchievements();
        assertEquals("Blobfish", achievement);
    }

    @Test
    public void testBelowMinAchievementP2() {
        Play play = new Play(testGame2, 2, 56);
        play.getAchievementsAndScores();
        String achievement = play.getAchievements();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(55);
        achievement = play.getAchievements();
        assertEquals("Blobfish", achievement);
    }

    @Test
    public void testBelowMinAchievementP3() {
        Play play = new Play(testGame3, 2, 2);
        play.getAchievementsAndScores();
        String achievement = play.getAchievements();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(1);
        achievement = play.getAchievements();
        assertEquals("Blobfish", achievement);
    }

}