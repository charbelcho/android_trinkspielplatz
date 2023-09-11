package com.charbelchougourou.trinkspielplatz;

public class SpielerPferderennen extends Spieler {

    private int schlucke;
    private String zeichen;

    public SpielerPferderennen() {

    }

    public SpielerPferderennen(String name, int schlucke, String zeichen) {
        super(name);
        this.schlucke = schlucke;
        this.zeichen = zeichen;
    }

    public int getSchlucke() {
        return schlucke;
    }

    public void setSchlucke(int schlucke) {
        this.schlucke = schlucke;
    }

    public String getZeichen() {
        return zeichen;
    }

    public void setZeichen(String zeichen) {
        this.zeichen = zeichen;
    }
}
