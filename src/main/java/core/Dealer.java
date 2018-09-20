package core;

public class Dealer extends Player {
    Dealer(String name) {
        super(name);
    }

    public void hitOrStand(Deck deck) {
        if (this.getCurrentHand().getTotal() <= 16 ||
            (this.getCurrentHand().getTotal() == 17 && this.getCurrentHand().hasSoftHand())) {
            this.hit(deck.draw());
            this.checkHitResult(this.getCurrentHand());
        } else {
            this.stand();
        }
    }

    @Override
    public void split(Deck deck) {
        if (this.getCurrentHand().getHand().get(0).getRank().equals(this.getCurrentHand().getHand().get(1).getRank()) &&
                this.getHand(0).getTotal() <= 17) {
            System.out.println(this.getName() + " is splitting.");
            super.split(deck);
        } else if (this.getCurrentHand().getHand().get(0).getRank().equals(this.getCurrentHand().getHand().get(1).getRank())) {
            System.out.println(this.getName() + " is not splitting.");
        }
    }
}
