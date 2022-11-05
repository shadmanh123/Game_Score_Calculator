package com.example.cmpt276project.model;

public enum Tiers {
    // Ocean Theme

    LEVEL1("Blobfish"),
    LEVEL2("Frogfish"),
    LEVEL3("Flamingo Tongue Snail"),
    LEVEL4("Peacock Mantis Shrimp"),
    LEVEL5("Giant Pacific Octopus"),
    LEVEL6("Lion's Mane Jellyfish"),
    LEVEL7("Bottlenose Dolphin"),
    LEVEL8("Colossal Squid"),
    LEVEL9("Great White Shark"),
    LEVEL10("Blue Whale");

    final String level;

    Tiers(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
