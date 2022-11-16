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
        Play play1 = new Play(testGame1, 3, 150, "normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        Play play2 = new Play(testGame2, 5, 700, "normal");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        Play play3 = new Play(testGame3, 1, 3, "normal");
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement3);
    }

    @Test
    public void testDifferentDifficultyAchievement(){
        Play play1 = new Play(testGame3, 1, 5, "normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Bottlenose Dolphin", achievement);

        Play play2 = new Play(testGame3, 1,5,"hard");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        Play play3 = new Play(testGame3, 1,5,"easy");
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Colossal Squid", achievement3);
    }


    @Test
    public void testMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 200,"normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 400,"normal");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 20,"normal");
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250, "normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 450, "normal");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 25, "normal");
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0, "normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 0, "normal");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blobfish", achievement2);

        Play play3 = new Play(testGame3, 2, 0,"normal");
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blobfish", achievement3);
    }
/*
    @Test
    public void testBelowMinAchievementP1() {
        Play play = new Play(testGame1, 2, 10);
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);

        play.setTotalScore(31);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);

        play.setTotalScore(32);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(33);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

    }
*/
    @Test
    public void testBelowMinAchievementP2() {
        Play play = new Play(testGame2, 2, 57, "normal");
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(56);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }

    @Test
    public void testBelowMinAchievementP3() {
        Play play = new Play(testGame3, 2, 1, "normal");
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(0);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }

}