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
        deck.add(new Card("herz2", 2, "rot"));
        deck.add(new Card("herz3", 3, "rot"));
        deck.add(new Card("herz4", 4, "rot"));
        deck.add(new Card("herz5", 5, "rot"));
        deck.add(new Card("herz6", 6, "rot"));
        deck.add(new Card("herz7", 7, "rot"));
        deck.add(new Card("herz8", 8, "rot"));
        deck.add(new Card("herz9", 9, "rot"));
        deck.add(new Card("herz10", 10, "rot"));
        deck.add(new Card("herzj", 11, "rot"));
        deck.add(new Card("herzq", 12, "rot"));
        deck.add(new Card("herzk", 13, "rot"));
        deck.add(new Card("herza", 14, "rot"));
        deck.add(new Card("karo2", 2, "rot"));
        deck.add(new Card("karo3", 3, "rot"));
        deck.add(new Card("karo4", 4, "rot"));
        deck.add(new Card("karo5", 5, "rot"));
        deck.add(new Card("karo6", 6, "rot"));
        deck.add(new Card("karo7", 7, "rot"));
        deck.add(new Card("karo8", 8, "rot"));
        deck.add(new Card("karo9", 9, "rot"));
        deck.add(new Card("karo10", 10, "rot"));
        deck.add(new Card("karoj", 11, "rot"));
        deck.add(new Card("karoq", 12, "rot"));
        deck.add(new Card("karok", 13, "rot"));
        deck.add(new Card("karoa", 14, "rot"));
        deck.add(new Card("pik2", 2, "schwarz"));
        deck.add(new Card("pik3", 3, "schwarz"));
        deck.add(new Card("pik4", 4, "schwarz"));
        deck.add(new Card("pik5", 5, "schwarz"));
        deck.add(new Card("pik6", 6, "schwarz"));
        deck.add(new Card("pik7", 7, "schwarz"));
        deck.add(new Card("pik8", 8, "schwarz"));
        deck.add(new Card("pik9", 9, "schwarz"));
        deck.add(new Card("pik10", 10, "schwarz"));
        deck.add(new Card("pikj", 11, "schwarz"));
        deck.add(new Card("pikq", 12, "schwarz"));
        deck.add(new Card("pikk", 13, "schwarz"));
        deck.add(new Card("pika", 14, "schwarz"));
        deck.add(new Card("kreuz2", 2, "schwarz"));
        deck.add(new Card("kreuz3", 3, "schwarz"));
        deck.add(new Card("kreuz4", 4, "schwarz"));
        deck.add(new Card("kreuz5", 5, "schwarz"));
        deck.add(new Card("kreuz6", 6, "schwarz"));
        deck.add(new Card("kreuz7", 7, "schwarz"));
        deck.add(new Card("kreuz8", 8, "schwarz"));
        deck.add(new Card("kreuz9", 9, "schwarz"));
        deck.add(new Card("kreuz10", 10, "schwarz"));
        deck.add(new Card("kreuzj", 11, "schwarz"));
        deck.add(new Card("kreuzq", 12, "schwarz"));
        deck.add(new Card("kreuzk", 13, "schwarz"));
        deck.add(new Card("kreuza", 14, "schwarz"));
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
