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

class PlayTest {

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
        Play play = new Play(testGame1, 3, 150, testOcean);
        assertEquals("OCEAN", testOcean.getClassName());
        System.out.println(testOcean.getClassName());
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        Tier testLand = Land.LEVEL1;
        play = new Play(testGame1, 3, 150, testLand);
        assertEquals("LAND", testLand.getClassName());
        play.getListOfAchievements();
        achievement = play.getAchievementScore();
        assertEquals("Chimpanzee", achievement);

        Tier testSky = Sky.LEVEL1;
        play = new Play(testGame1, 3, 150, testSky);
        assertEquals("SKY", testSky.getClassName());
        play.getListOfAchievements();
        achievement = play.getAchievementScore();
        assertEquals("Bald Eagle", achievement);
        testGame3 = new Game("Some Card Game", 0, 7);
    }

    @Test
    public void testSimpleAchievementP1() {
        Play play1 = new Play(testGame1, 3, 150, testOcean);
        System.out.println(testOcean.getClassName());
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        Play play2 = new Play(testGame2, 5, 700, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        Play play3 = new Play(testGame3, 1, 5, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Peacock Mantis Shrimp", achievement3);
    }


    @Test
    public void testMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 200, testOcean);
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 400, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 20, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250, testOcean);
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 450, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 25, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0, testOcean);
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 0, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blobfish", achievement2);

        Play play3 = new Play(testGame3, 2, 0, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blobfish", achievement3);
    }

    @Test
    public void testBelowMinAchievementP1() {
        Play play = new Play(testGame1, 2, 10, testOcean);
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

    @Test
    public void testBelowMinAchievementP2() {
        Play play = new Play(testGame2, 2, 56, testOcean);
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(55);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }

    @Test
    public void testBelowMinAchievementP3() {
        Play play = new Play(testGame3, 2, 2, testOcean);
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(1);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }



}