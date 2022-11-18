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
import java.util.List;


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
        List<Integer> scores = new ArrayList<>();
        scores.add(20);
        scores.add(40);
        scores.add(90);
        Play play1 = new Play(testGame1, 3, scores, testOcean);
        System.out.println(testOcean.getClassName());
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        scores.clear();
        scores.add(90);
        scores.add(120);
        scores.add(300);
        scores.add(50);
        scores.add(140);
        Play play2 = new Play(testGame2, 5, scores, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        scores.clear();
        scores.add(3);
        Play play3 = new Play(testGame3, 1, scores);
        Play play3 = new Play(testGame3, 1, 5, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Peacock Mantis Shrimp", achievement3);
    }


    @Test
    public void testMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 200, testOcean);
        play1.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(150);
        scores.add(50);
        Play play1 = new Play(testGame1, 2, scores);

        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        scores.clear();
        scores.add(230);
        scores.add(170);
        Play play2 = new Play(testGame2, 2, scores);

        Play play2 = new Play(testGame2, 2, 400, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        scores.clear();
        scores.add(13);
        scores.add(7);
        Play play3 = new Play(testGame3, 2, scores);
        Play play3 = new Play(testGame3, 2, 20, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        List<Integer> scores = new ArrayList<>();
        scores.add(150);
        scores.add(100);
        Play play1 = new Play(testGame1, 2, scores);
        Play play1 = new Play(testGame1, 2, 250, testOcean);
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        scores.clear();
        scores.add(370);
        scores.add(80);
        Play play2 = new Play(testGame2, 2, scores);
        Play play2 = new Play(testGame2, 2, 450, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        scores.clear();
        scores.add(20);
        scores.add(5);
        Play play3 = new Play(testGame3, 2, scores);
        Play play3 = new Play(testGame3, 2, 25, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0, testOcean);
        play1.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);
        Play play1 = new Play(testGame1, 2, scores);
        String achievement = play1.getAchievementScore();
        assertEquals("Blobfish", achievement);

        scores.clear();
        scores.add(0);
        scores.add(0);
        Play play2 = new Play(testGame2, 2, scores);
        Play play2 = new Play(testGame2, 2, 0, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blobfish", achievement2);

        scores.clear();
        scores.add(0);
        scores.add(0);
        Play play3 = new Play(testGame3, 2, scores);
        Play play3 = new Play(testGame3, 2, 0, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blobfish", achievement3);
    }

    @Test
    public void testBelowMinAchievementP1() {
        Play play = new Play(testGame1, 2, 10, testOcean);
        play.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(8);
        scores.add(2);
        Play play = new Play(testGame1, 2, scores);
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
        List<Integer> scores = new ArrayList<>();
        scores.add(34);
        scores.add(22);
        Play play = new Play(testGame2, 2, scores);
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
        List<Integer> scores = new ArrayList<>();
        scores.add(2);
        scores.add(0);
        Play play = new Play(testGame3, 2, scores);
        Play play = new Play(testGame3, 2, 2, testOcean);
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(1);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }



}