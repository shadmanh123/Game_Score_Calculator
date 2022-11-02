package com.example.cmpt276project.model;

public enum Tiers {
    // Ocean Theme

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

    Tiers(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
