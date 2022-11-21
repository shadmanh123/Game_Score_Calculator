package com.example.cmpt276project.model.tiers;

public enum Sky implements Tier {
    LEVEL10("Ruppell’s Griffon Vulture"),
    LEVEL9("Peregrine Falcon"),
    LEVEL8("Black Crowned Crane"),
    LEVEL7("Whooper Swan"),
    LEVEL6("Alpine Chough"),
    LEVEL5("Bald Eagle"),
    LEVEL4("Barn Owl"),
    LEVEL3("Little Brown Bat"),
    LEVEL2("Red Carneau Pigeon"),
    LEVEL1("Dorking Chicken");

    final String level;

    Sky(String level) {
        this.level = level;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getClassName() {
        return "Sky";
    }
}
