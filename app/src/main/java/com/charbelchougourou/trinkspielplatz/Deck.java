package com.charbelchougourou.trinkspielplatz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;

    public Deck() {
    }
    
    public Deck(List<Card> deck) {
        this.deck = deck;
    }
    
    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
    
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        deck.add(new Card(1, "herz2", 2, "rot", "herz"));
        deck.add(new Card(2, "herz3", 3, "rot", "herz"));
        deck.add(new Card(3, "herz4", 4, "rot", "herz"));
        deck.add(new Card(4, "herz5", 5, "rot", "herz"));
        deck.add(new Card(5, "herz6", 6, "rot", "herz"));
        deck.add(new Card(6, "herz7", 7, "rot", "herz"));
        deck.add(new Card(7, "herz8", 8, "rot", "herz"));
        deck.add(new Card(8, "herz9", 9, "rot", "herz"));
        deck.add(new Card(9, "herz10", 10, "rot", "herz"));
        deck.add(new Card(10, "herzj", 11, "rot", "herz"));
        deck.add(new Card(11, "herzq", 12, "rot", "herz"));
        deck.add(new Card(12, "herzk", 13, "rot", "herz"));
        deck.add(new Card(13, "herza", 14, "rot", "herz"));
        deck.add(new Card(14, "karo2", 2, "rot", "karo"));
        deck.add(new Card(15, "karo3", 3, "rot", "karo"));
        deck.add(new Card(16, "karo4", 4, "rot", "karo"));
        deck.add(new Card(17, "karo5", 5, "rot", "karo"));
        deck.add(new Card(18, "karo6", 6, "rot", "karo"));
        deck.add(new Card(19, "karo7", 7, "rot", "karo"));
        deck.add(new Card(20, "karo8", 8, "rot", "karo"));
        deck.add(new Card(21, "karo9", 9, "rot", "karo"));
        deck.add(new Card(22, "karo10", 10, "rot", "karo"));
        deck.add(new Card(23, "karoj", 11, "rot", "karo"));
        deck.add(new Card(24, "karoq", 12, "rot", "karo"));
        deck.add(new Card(25, "karok", 13, "rot", "karo"));
        deck.add(new Card(26, "karoa", 14, "rot", "karo"));
        deck.add(new Card(27, "pik2", 2, "schwarz", "pik"));
        deck.add(new Card(28, "pik3", 3, "schwarz", "pik"));
        deck.add(new Card(29, "pik4", 4, "schwarz", "pik"));
        deck.add(new Card(30, "pik5", 5, "schwarz", "pik"));
        deck.add(new Card(31, "pik6", 6, "schwarz", "pik"));
        deck.add(new Card(32, "pik7", 7, "schwarz", "pik"));
        deck.add(new Card(33, "pik8", 8, "schwarz", "pik"));
        deck.add(new Card(34, "pik9", 9, "schwarz", "pik"));
        deck.add(new Card(35, "pik10", 10, "schwarz", "pik"));
        deck.add(new Card(36, "pikj", 11, "schwarz", "pik"));
        deck.add(new Card(37, "pikq", 12, "schwarz", "pik"));
        deck.add(new Card(38, "pikk", 13, "schwarz", "pik"));
        deck.add(new Card(39, "pika", 14, "schwarz", "pik"));
        deck.add(new Card(40, "kreuz2", 2, "schwarz", "kreuz"));
        deck.add(new Card(41, "kreuz3", 3, "schwarz", "kreuz"));
        deck.add(new Card(42, "kreuz4", 4, "schwarz", "kreuz"));
        deck.add(new Card(43, "kreuz5", 5, "schwarz", "kreuz"));
        deck.add(new Card(44, "kreuz6", 6, "schwarz", "kreuz"));
        deck.add(new Card(45, "kreuz7", 7, "schwarz", "kreuz"));
        deck.add(new Card(46, "kreuz8", 8, "schwarz", "kreuz"));
        deck.add(new Card(47, "kreuz9", 9, "schwarz", "kreuz"));
        deck.add(new Card(48, "kreuz10", 10, "schwarz", "kreuz"));
        deck.add(new Card(49, "kreuzj", 11, "schwarz", "kreuz"));
        deck.add(new Card(50, "kreuzq", 12, "schwarz", "kreuz"));
        deck.add(new Card(51, "kreuzk", 13, "schwarz", "kreuz"));
        deck.add(new Card(52, "kreuza", 14, "schwarz", "kreuz"));
        return deck;
    }

    public static List<Card> randomizeListOfCards(List<Card> listToRandomize) {
        Collections.shuffle(listToRandomize);
        List<Card> randomList = new ArrayList<>();
        for (Card card : listToRandomize) {
            randomList.add(card);
        }
        return randomList;
    }

    public static List<Card> randomizeListOfCardsPferderennen(List<Card> listToRandomize) {
        Collections.shuffle(listToRandomize);
        List<Card> randomList = new ArrayList<>();
        for (Card card : listToRandomize) {
            if (card.getValue() < 14) {
                randomList.add(card);
            }
        }
        return randomList;
    }

    

}
