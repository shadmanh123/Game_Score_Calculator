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
        testGame3 = new Game("Some Card Game", 0, 10);
        testOcean = Ocean.LEVEL1;
    }

    @Test
    public void testSimpleAchievementP1() {
        Play play1 = new Play(testGame1, 3, 150, "normal");
        play1.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(20);
        scores.add(40);
        scores.add(90);
        Play play1 = new Play(testGame1, 3, scores, testOcean);
        System.out.println(testOcean.getClassName());
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        Play play2 = new Play(testGame2, 5, 700, "normal");
        play2.getListOfAchievements();
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

        Play play3 = new Play(testGame3, 1, 3, "normal");
        play3.getListOfAchievements();
        scores.clear();
        scores.add(3);
        Play play3 = new Play(testGame3, 1, scores, testOcean);
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
        List<Integer> scores = new ArrayList<>();
        scores.add(150);
        scores.add(50);
        Play play1 = new Play(testGame1, 2, scores, testOcean);
        play1.getListOfAchievements();

        Play play1 = new Play(testGame1, 2, 200,"normal");
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        scores.clear();
        scores.add(230);
        scores.add(170);

        Play play2 = new Play(testGame2, 2, scores, testOcean);
        play2.getListOfAchievements();
        Play play2 = new Play(testGame2, 2, 400,"normal");
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 20,"normal");
        play3.getListOfAchievements();
        scores.clear();
        scores.add(13);
        scores.add(7);
        Play play3 = new Play(testGame3, 2, scores, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        Play play1 = new Play(testGame1, 2, 250, "normal");
        play1.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(150);
        scores.add(100);
        Play play1 = new Play(testGame1, 2, scores, testOcean);
        play1.getListOfAchievements();
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        Play play2 = new Play(testGame2, 2, 450, "normal");
        play2.getListOfAchievements();
        scores.clear();
        scores.add(370);
        scores.add(80);
        Play play2 = new Play(testGame2, 2, scores, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        Play play3 = new Play(testGame3, 2, 25, "normal");
        play3.getListOfAchievements();
        scores.clear();
        scores.add(20);
        scores.add(5);
        Play play3 = new Play(testGame3, 2, scores, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
        Play play1 = new Play(testGame1, 2, 0, "normal");
        play1.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);
        Play play1 = new Play(testGame1, 2, scores, testOcean);
        play1.getListOfAchievements();

        String achievement = play1.getAchievementScore();
        assertEquals("Blobfish", achievement);

        Play play2 = new Play(testGame2, 2, 0, "normal");
        play2.getListOfAchievements();
        scores.clear();
        scores.add(0);
        scores.add(0);
        Play play2 = new Play(testGame2, 2, scores, testOcean);
        play2.getListOfAchievements();
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blobfish", achievement2);

        Play play3 = new Play(testGame3, 2, 0,"normal");
        play3.getListOfAchievements();
        scores.clear();
        scores.add(0);
        scores.add(0);
        Play play3 = new Play(testGame3, 2, scores, testOcean);
        play3.getListOfAchievements();
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blobfish", achievement3);
    }

    @Test
    public void testBelowMinAchievementP1() {
        Play play = new Play(testGame1, 2, 10, "normal");
        play.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(8);
        scores.add(2);
        Play play = new Play(testGame1, 2, scores, testOcean);
        play.getListOfAchievements();

        String achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);

        play.setTotalScore(31);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(32);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(33);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

    }

    @Test
    public void testBelowMinAchievementP2() {
        Play play = new Play(testGame2, 2, 57, "normal");
        play.getListOfAchievements();
        List<Integer> scores = new ArrayList<>();
        scores.add(34);
        scores.add(22);
        Play play = new Play(testGame2, 2, scores, testOcean);
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
        List<Integer> scores = new ArrayList<>();
        scores.add(2);
        scores.add(0);
        Play play = new Play(testGame3, 2, scores, testOcean);
        play.getListOfAchievements();
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(0);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }



}