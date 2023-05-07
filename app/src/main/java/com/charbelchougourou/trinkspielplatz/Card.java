package com.charbelchougourou.trinkspielplatz;

public class Card {
    private String name;
    private int value;
    private String farbe;

    public Card() {

    }

    public Card(String name, int value, String farbe) {
        this.name = name;
        this.value = value;
        this.farbe = farbe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
