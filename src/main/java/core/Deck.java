package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deck;

	Deck() {
		deck = new ArrayList<>();
	}

	public List<Card> getDeck() {
		return this.deck;
	}

	public void create() {
		for (char suit : Globals.suites) {
			for (String rank : Globals.ranks) {
				this.deck.add(new Card(suit, rank));
			}
		}
	}

    public void create(List<Card> cards) {
        for (Card card : cards) {
            this.deck.add(card);
        }
    }

	public Card draw() {
		return this.deck.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(this.deck);
	}
}
