package com.charbelchougourou.trinkspielplatz;

public class Card {
    private int id;
    private String card;
    private int value;
    private String farbe;
    private String zeichen;

    public Card() {

    }

    public Card(int id, String card, int value, String farbe, String zeichen) {
        this.id = id;
        this.card = card;
        this.value = value;
        this.farbe = farbe;
        this.zeichen = zeichen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public String getZeichen() {
        return zeichen;
    }

    public void setZeichen(String zeichen) {
        this.zeichen = zeichen;
    }


}
