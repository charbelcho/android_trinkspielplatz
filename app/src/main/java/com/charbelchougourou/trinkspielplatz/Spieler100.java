package com.charbelchougourou.trinkspielplatz;

public class Spieler100 extends Spieler {

    private int punkte;

    public Spieler100() {

    }

    public Spieler100(String name) {
        super(name);
        this.punkte = 0;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }
}
