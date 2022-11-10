package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.Tiers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TierTest {

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
    public void test() {
        HashMap<Tiers, Integer> achievements = testGame1.getListOfAchievements(1);
        for(Integer min: achievements.values()) {
            System.out.println();
        }
    }
}
