package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;
import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Play;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


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
        List<Integer> scores = new ArrayList<>();
        scores.add(20);
        scores.add(40);
        scores.add(90);
        Play play1 = new Play(testGame1, 3, scores);
        String achievement = play1.getAchievementScore();
        assertEquals("Giant Pacific Octopus", achievement);

        scores.clear();
        scores.add(90);
        scores.add(120);
        scores.add(300);
        scores.add(50);
        scores.add(140);
        Play play2 = new Play(testGame2, 5, scores);
        String achievement2 = play2.getAchievementScore();
        assertEquals("Lion's Mane Jellyfish", achievement2);

        scores.clear();
        scores.add(3);
        Play play3 = new Play(testGame3, 1, scores);
        String achievement3 = play3.getAchievementScore();
        assertEquals("Peacock Mantis Shrimp", achievement3);
    }


    @Test
    public void testMaxAchievement() {
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

        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        scores.clear();
        scores.add(13);
        scores.add(7);
        Play play3 = new Play(testGame3, 2, scores);
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testAboveMaxAchievement() {
        List<Integer> scores = new ArrayList<>();
        scores.add(150);
        scores.add(100);
        Play play1 = new Play(testGame1, 2, scores);
        String achievement = play1.getAchievementScore();
        assertEquals("Blue Whale", achievement);

        scores.clear();
        scores.add(370);
        scores.add(80);
        Play play2 = new Play(testGame2, 2, scores);
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blue Whale", achievement2);

        scores.clear();
        scores.add(20);
        scores.add(5);
        Play play3 = new Play(testGame3, 2, scores);
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blue Whale", achievement3);
    }

    @Test
    public void testZeroAchievement() {
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
        String achievement2 = play2.getAchievementScore();
        assertEquals("Blobfish", achievement2);

        scores.clear();
        scores.add(0);
        scores.add(0);
        Play play3 = new Play(testGame3, 2, scores);
        String achievement3 = play3.getAchievementScore();
        assertEquals("Blobfish", achievement3);
    }

    @Test
    public void testBelowMinAchievementP1() {
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
        String achievement = play.getAchievementScore();
        assertEquals("Frogfish", achievement);

        play.setTotalScore(1);
        achievement = play.getAchievementScore();
        assertEquals("Blobfish", achievement);
    }

}