package com.example.cmpt276project;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cmpt276project.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    Game testGame1;
    Game testGame2;
    Game testGame3;

    @BeforeEach
    public void runBefore() {
        testGame1 = new Game("Some Card Game", 0, 10);
        testGame2 = new Game("Poker", 100, 200);
        testGame3 = new Game("Chutes and Ladders", 20, 100);

    }

    @Test
    public void testSimpleAchievementList() {


    }

}