package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Options;
import com.example.cmpt276project.model.tiers.Land;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Sky;
import com.example.cmpt276project.model.tiers.Tier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tier Test: holds coverage tests for the tiers
 */
public class TierTest {
    Game testGame;
    Tier testOcean;
    Options testOption;

    @BeforeEach
    public void runBefore() {
        testGame = new Game("Chutes and Ladders", 30, 100);
        testOcean = Ocean.LEVEL1;
        testOption = new Options("normal", testOcean);
    }

    @Test
    public void testOceanTierTypes() {
        List<Double> scores = new ArrayList<>();
        scores.add(20.0);
        scores.add(40.0);
        scores.add(90.0);
        Play play = new Play(testGame, 3, scores, testOption);
        assertEquals("Ocean", testOcean.getClassName());
        System.out.println(testOcean.getClassName());
        String achievement = play.getAchievementScore(testOption.getTheme());
        assertEquals("Giant Pacific Octopus", achievement);
    }

    @Test
    public void testLandTierTypes() {
        List<Double> scores = new ArrayList<>();
        scores.add(20.0);
        scores.add(40.0);
        scores.add(90.0);
        testOption.setTheme(Land.LEVEL1);
        Play play = new Play(testGame, 3, scores, testOption);
        String tier = testOption.getThemeName();
        assertEquals("Land", tier);
        String achievement = play.getAchievementScore(testOption.getTheme());
        assertEquals("Chimpanzee", achievement);
    }

    @Test
    public void testSkyTierTypes() {
        List<Double> scores = new ArrayList<>();
        scores.add(20.0);
        scores.add(40.0);
        scores.add(90.0);
        testOption.setTheme(Sky.LEVEL1);
        Play play = new Play(testGame, 3, scores, testOption);
        String tier = testOption.getThemeName();
        assertEquals("Sky", tier);
        String achievement = play.getAchievementScore(testOption.getTheme());
        assertEquals("Bald Eagle", achievement);
    }
}
