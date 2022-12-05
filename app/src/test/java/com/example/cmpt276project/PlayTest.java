/*
package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Options;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Tier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Play Test: holds coverage tests for the play class
 *//*

class PlayTest {
    Game testGame1;
    Game testGame2;
    Game testGame3;
    Tier testOcean;
    Options testOption;

    @BeforeEach
    public void runBefore() {
        testGame1 = new Game("Chutes and Ladders", 30, 100);
        testGame2 = new Game("Poker", 100, 200);
        testGame3 = new Game("Some Card Game", 0, 10);
        testOcean = Ocean.LEVEL1;
        testOption = new Options("normal", testOcean);
    }

    @Test
    public void testSimpleAchievementP1() {
        List<Double> scores = new ArrayList<>();
        scores.add(20.0);
        scores.add(40.0);
        scores.add(90.0);
        Play play1 = new Play(testGame1, 3, scores, testOption);
        Tier achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement.getLevel());

        scores.clear();
        scores.add(90.0);
        scores.add(120.0);
        scores.add(300.0);
        scores.add(50.0);
        scores.add(140.0);
        Play play2 = new Play(testGame2, 5, scores, testOption);
        achievement = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement.getLevel());

        scores.clear();
        scores.add(3.0);
        Play play3 = new Play(testGame3, 1, scores, testOption);
        achievement = play3.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement.getLevel());
    }

    @Test
    public void testDifferentDifficultyAchievement(){
        List<Double> scores = new ArrayList<>();
        scores.add(5.0);
        Play play1 = new Play(testGame3, 1, scores, testOption);
        Tier achievement = play1.getAchievementScore();
        assertEquals("Bottlenose Dolphin", achievement.getLevel());

        scores.clear();
        scores.add(5.0);
        testOption.setDifficulty("hard");
        Play play2 = new Play(testGame3, 1, scores, testOption);
        achievement = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement.getLevel());

        scores.clear();
        scores.add(5.0);
        testOption.setDifficulty("easy");
        Play play3 = new Play(testGame3, 1, scores, testOption);
        achievement = play3.getAchievementScore();
        assertEquals("Colossal Squid", achievement.getLevel());
    }

    @Test
    public void testMaxAchievement() {
        List<Double> scores = new ArrayList<>();
        scores.add(150.0);
        scores.add(50.0);
        Play play1 = new Play(testGame1, 2, scores, testOption);
        Tier achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());

        scores.clear();
        scores.add(230.0);
        scores.add(170.0);
        Play play2 = new Play(testGame2, 2, scores, testOption);
        achievement = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());

        scores.clear();
        scores.add(13.0);
        scores.add(7.0);
        Play play3 = new Play(testGame3, 2, scores, testOption);
        achievement = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());
    }

    @Test
    public void testAboveMaxAchievement() {
        List<Double> scores = new ArrayList<>();
        scores.add(150.0);
        scores.add(100.0);
        Play play1 = new Play(testGame1, 2, scores, testOption);
        Tier achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());

        scores.clear();
        scores.add(370.0);
        scores.add(80.0);
        Play play2 = new Play(testGame2, 2, scores, testOption);
        achievement = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());

        scores.clear();
        scores.add(20.0);
        scores.add(5.0);
        Play play3 = new Play(testGame3, 2, scores, testOption);
        achievement = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement.getLevel());
    }

    @Test
    public void testZeroAchievement() {
        List<Double> scores = new ArrayList<>();
        scores.add(0.0);
        scores.add(0.0);
        Play play1 = new Play(testGame1, 2, scores, testOption);

        Tier achievement = play1.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());

        scores.clear();
        scores.add(0.0);
        scores.add(0.0);
        Play play2 = new Play(testGame2, 2, scores, testOption);
        achievement = play2.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());

        scores.clear();
        scores.add(0.0);
        scores.add(0.0);
        Play play3 = new Play(testGame3, 2, scores, testOption);
        achievement = play3.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());
    }

    @Test
    public void testBelowMinAchievementP1() {
        List<Double> scores = new ArrayList<>();
        scores.add(8.0);
        scores.add(2.0);
        Play play = new Play(testGame1, 2, scores, testOption);

        Tier achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());

        play.setTotalScore(31.0);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement.getLevel());

        play.setTotalScore(32.0);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement.getLevel());

        play.setTotalScore(33.0);
        achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement.getLevel());

    }

    @Test
    public void testBelowMinAchievementP2() {
        List<Double> scores = new ArrayList<>();
        scores.add(35.0);
        scores.add(22.0);
        Play play = new Play(testGame2, 2, scores, testOption);
        Tier achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement.getLevel());

        play.setTotalScore(56.0);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());
    }

    @Test
    public void testBelowMinAchievementP3() {
        List<Double> scores = new ArrayList<>();
        scores.add(1.0);
        scores.add(0.0);
        Play play = new Play(testGame3, 2, scores, testOption);
        Tier achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement.getLevel());

        play.setTotalScore(0.0);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement.getLevel());
    }

}
*/
