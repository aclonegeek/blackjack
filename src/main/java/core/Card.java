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
}
