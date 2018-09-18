package core;

public class Card {
	private char suit;
	private String rank;

	Card(char suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public char getSuit() {
		return this.suit;
	}

	public String getRank() {
		return this.rank;
	}

	@Override
	// Code inspired from: https://www.geeksforgeeks.org/overriding-equals-method-in-java/
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Card)) {
			return false;
		}

		Card otherCard = (Card)o;
		return this.suit == otherCard.suit && this.rank == otherCard.rank;
	}
}
