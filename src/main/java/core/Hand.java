package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
	private List<Card> hand;
	private boolean hasSoftHand;

	Hand() {
		this.hand = new ArrayList<>();
		this.hasSoftHand = false;
	}

	public String toString() {
		// Code inspired from: https://www.baeldung.com/java-list-to-string
		return this.hand.stream().map(Object::toString).collect(Collectors.joining(", ", "[", "]"));
	}

	public List<Card> getHand() {
		return this.hand;
	}

	public boolean hasSoftHand() {
		return this.hasSoftHand;
	}

	public int getTotal() {
		int total = 0;
		List<Card> aces = new ArrayList<>(); 

		for (Card card : this.hand) {
			String rank = card.getRank();

			// 2 - 10.
			if (rank.chars().allMatch(Character::isDigit)) {
				total += Integer.parseInt(rank);
			// Jack, Queen, King.
			} else if (rank.equals("J") || rank.equals("Q") || rank.equals("K") ) {
				total += 10;
			// Ace.
			} else if (rank.equals("A")) {
				// We will deal with aces after everything else has been calculated.
				aces.add(card);
			}
		}

		// We have 2 aces, but setting one to 11 and the other to 1 busts,
		// so they both need to count as 1 each.
		if (aces.size() > 1 && total + 12 > 21) {
			total += 2;
		} else {
			for (int i = 0; i < aces.size(); i++) {
				if (total + 11 > 21) {
					total += 1;
				} else {
					total += 11;
					this.hasSoftHand = true;
				}
			}
		}

		return total;
	}

	public void addCard(Card card) {
		this.hand.add(card);
	}

	public Card popCard() {
		return this.hand.remove(this.hand.size() - 1);
	}
}
