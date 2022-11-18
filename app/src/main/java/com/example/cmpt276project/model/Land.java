package com.example.cmpt276project.model;

public enum Land implements Tier {
    LEVEL10("African Bush Elephant"),
    LEVEL9("White Rhinoceros"),
    LEVEL8("Siberian Tiger"),
    LEVEL7("Impala Antelope"),
    LEVEL6("Red Kangaroo"),
    LEVEL5("Chimpanzee"),
    LEVEL4("Blue Peacock"),
    LEVEL3("Queensland Koala"),
    LEVEL2("Northern Flying Squirrel"),
    LEVEL1("Etruscan shrew");

    final String level;

    Land(String level) {
        this.level = level;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getClassName() {
        return "LAND";
    }
}
