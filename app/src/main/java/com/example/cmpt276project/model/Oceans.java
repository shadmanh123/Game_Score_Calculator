package com.example.cmpt276project.model;

import java.util.Arrays;
import java.util.List;

public enum Oceans  {
    LEVEL10("Blue Whale"),
    LEVEL9("Great White Shark"),
    LEVEL8("Colossal Squid"),
    LEVEL7("Bottlenose Dolphin"),
    LEVEL6("Lion's Mane Jellyfish"),
    LEVEL5("Giant Pacific Octopus"),
    LEVEL4("Peacock Mantis Shrimp"),
    LEVEL3("Flamingo Tongue Snail"),
    LEVEL2("Frogfish"),
    LEVEL1("Blobfish");

    final String level;

    Oceans(String level) {
        this.level = level;
    }

    // @Override
    public String getLevel() {
        return level;
    }

    // @Override
    public List<Oceans> getValues() {
        return Arrays.asList(values());
    }


}
