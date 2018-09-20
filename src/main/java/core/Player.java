package core;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Hand> hands;
    private Hand currentHand;

	private boolean hasSecondHand;

	enum State {
		PLAYING,
		BUSTED,
		STANDING,
		WON
	}
	private State state;

	enum WhichHand {
		FIRST,
		SECOND
	}
	private WhichHand whichHand;

	Player(String name) {
		this.name = name;
		this.hands = new ArrayList<>();
		this.hasSecondHand = false;
		this.state = State.PLAYING;
        this.whichHand = WhichHand.FIRST;
	}

    public Hand getCurrentHand() {
        return this.currentHand;
    }

    public void setCurrentHand(Hand currentHand) {
        this.currentHand = currentHand;
    }

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public WhichHand getWhichHand() {
		return this.whichHand;
	}

	public void setWhichHand(WhichHand whichHand) {
		this.whichHand = whichHand;
	}

	public boolean hasSecondHand() {
		return this.hasSecondHand;
	}

	public void setHasSecondHand(boolean hasSecondHand) {
		this.hasSecondHand = hasSecondHand;
	} 

	public void createHand() {
		this.hands.add(new Hand());
	}
	
	public String getName() {
		return this.name;
	}

	public int getBestHandTotal() {
		int total = 0;
		for (Hand hand : this.hands) {
			if (hand.getTotal() <= 21 && hand.getTotal() > total) {
				total = hand.getTotal();
			}
		}
		return total;
	}

	public Hand getHand(int index) {
		return this.hands.get(index);
	}

	public List<Hand> getHands() {
		return this.hands;
	}

    public boolean switchHand(Deck deck) {
        if ((this.state == State.BUSTED || this.state == State.STANDING) &&
            this.hasSecondHand &&
            this.whichHand == WhichHand.FIRST) {
            this.outputHandWithTotal(this.currentHand);
            this.currentHand = this.getHand(1);
            this.currentHand.addCard(deck.draw());
            this.outputDraw(this.getHand(1).getHand());
            this.whichHand = WhichHand.SECOND;
            this.state = State.PLAYING;
            return true;
        }
        return false;
    }

	public void hit(Card card) {
        System.out.println(this.name + " hits.");
		this.currentHand.addCard(card);
		this.outputDraw(this.currentHand.getHand());
        this.checkHitResult(this.currentHand);
	}

    protected void checkHitResult(Hand hand) {
        if (hand.getTotal() > 21) {
            this.state = State.BUSTED;
        } else if (hand.getTotal() == 21) {
            this.state = State.WON;
        }
    }

    public void stand() {
        this.state = State.STANDING;
    }

	public void split(Deck deck) {
        if (this.currentHand.getHand().get(0).getRank().equals(this.currentHand.getHand().get(1).getRank())) {
            this.createHand();
            this.getHand(1).addCard(this.getHand(0).popCard());
            this.getHand(0).addCard(deck.draw());
            this.outputDraw(this.getHand(0).getHand());
            this.hasSecondHand = true;
        }
	}

	public void outputDraw(List<Card> hand) {
		System.out.println(this.name + " drew " + hand.get(hand.size() - 1)); 
	}

	public void outputHand(Hand hand) {
		System.out.println(this.name + " hand: " + hand.toString());
	}

	public void outputTotal(Hand hand) {
		System.out.println(this.name + " score: " + hand.getTotal());
	}

	public void outputHandWithTotal(Hand hand) {
		System.out.println(this.name + " hand: " + hand.toString() + ", score: " + hand.getTotal());
	}

	public void outputHands() {
		System.out.println("===" + this.name + "===");

		int handCount = 1;
		for (Hand hand : this.hands) {
			System.out.print("\tHand " + handCount++ + ": ");
			System.out.println(hand.toString() + ", total: " + hand.getTotal());
		}
	}
}
