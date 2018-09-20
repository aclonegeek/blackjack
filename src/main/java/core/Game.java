package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import core.Player.State;
import core.Player.WhichHand;

public class Game {
    private Deck deck;

    private Player player;
    private Dealer dealer;

    private Scanner input;
    private String choice;

    public Player getPlayer() {
        return this.player;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public void start() {
        this.input = new Scanner(System.in);
        this.choice = new String();

        System.out.println("=== Blackjack ===");

        while (!this.choice.equals("c") || !this.choice.equals("f")) {
            System.out.print("Console input (c) or file input (f): ");

            this.choice = input.nextLine();
            System.out.println();

            if (this.choice.equals("c")) {
                playWithConsole();
                break;
            } else if (this.choice.equals("f")) {
                playWithFile(null);
                break;
            }
        }

        this.input.close();
    }
    
    public void setupGame(boolean customDeck) {
        if (!customDeck) {
            this.deck = new Deck();
            this.deck.create();
            this.deck.shuffle();
        }

        this.player = new Player("Player");
        this.dealer = new Dealer("Dealer");
        this.player.createHand();
        this.dealer.createHand();
        this.player.setCurrentHand(this.player.getHand(0));
        this.dealer.setCurrentHand(this.dealer.getHand(0));

        // Draw initial cards.
        this.player.getHand(0).addCard(deck.draw());
        this.player.outputDraw(player.getHand(0).getHand());
        this.player.getHand(0).addCard(deck.draw());
        this.player.outputDraw(player.getHand(0).getHand());
        this.player.outputHandWithTotal(player.getHand(0));

        this.dealer.getHand(0).addCard(deck.draw());
        this.dealer.outputDraw(dealer.getHand(0).getHand());
        this.dealer.getHand(0).addCard(deck.draw());
    }

    public void handleSplitting() {
        Hand playerHand = this.player.getCurrentHand();
        if (playerHand.getHand().get(0).getRank().equals(playerHand.getHand().get(1).getRank())) {
            System.out.print("Enter D to split: ");
            this.choice = this.input.nextLine();

            if (this.choice.equals("D")) {
                System.out.println(this.player.getName() + " is splitting.");
                this.player.split(this.deck);
            } else {
                System.out.println(this.player.getName() + " is not splitting.");
            }
        }

        this.dealer.split(deck);
    }

    public boolean initialWin() {
        if (this.player.getBestHandTotal() == 21) {
            this.player.setState(State.WON);
        }
        if (this.dealer.getBestHandTotal() == 21) {
            this.dealer.setState(State.WON);
        }

        if (this.player.getState() == State.WON && this.dealer.getState() != State.WON) {
            System.out.println("Player has blackjack!");
            this.player.outputHands();
            this.dealer.outputHands();
            System.out.println("Player wins!");
            return true;
        } else if (this.dealer.getState() == State.WON) {
            System.out.println("Dealer has blackjack!");
            this.player.outputHands();
            this.dealer.outputHands();
            System.out.println("Dealer wins!");
            this.player.setState(State.BUSTED);
            return true;
        }
        return false;
    }

    public void determineStandingWinner() {
        this.player.outputHands();
        this.dealer.outputHands();

        if (this.player.getBestHandTotal() > dealer.getBestHandTotal()) {
            System.out.println("Player's best hand has a score of " + this.player.getBestHandTotal() + " while dealer's best hand has a score of " + this.dealer.getBestHandTotal() + ".");
            System.out.println("Player wins!");
            this.player.setState(State.WON);
        } else {
            System.out.println("Dealer's best hand has a score of " + this.dealer.getBestHandTotal() + " while player's best hand has a score of " + this.player.getBestHandTotal() + ".");
            System.out.println("Dealer wins!");
            this.dealer.setState(State.WON);
        }
    }

    public void playerTurn() {
        while (true) {
            System.out.println();
            System.out.print("Hit (H) or Stand (S): ");
            this.choice = this.input.nextLine();
            System.out.println();

            if (this.choice.equals("H")) {
                //                System.out.println("Player hits.");
                this.player.hit(this.deck.draw());
                this.player.outputHandWithTotal(this.player.getCurrentHand());
            } else if (this.choice.equals("S")) {
                System.out.println("Player stands.");
                this.player.stand();
                this.player.outputHandWithTotal(this.player.getCurrentHand());
            // Invalid input. Ask again.
            } else {
                System.out.println("Please enter H to hit or S to stand.");
                continue;
            }

            if (this.player.getState() == State.WON) {
                break;                
            }

            if (this.player.switchHand(this.deck)) {
                this.player.outputHandWithTotal(this.player.getCurrentHand());
                continue;
            }

            if (this.player.getState() == State.BUSTED) {
                if (this.player.getWhichHand() == WhichHand.SECOND) {
                    System.out.println("Player's second hand is busted.");
                } else {
                    System.out.println("Player's hand is busted.");
                }
                break;
            } else if (this.player.getState() == State.STANDING) {
                break;
            }
        }
    }

    public void dealerTurn() {
        while (true) {
            // Hit.
            this.dealer.hitOrStand(this.deck);

            if (this.dealer.getState() == State.WON) {
                break;                
            } else if (this.dealer.getState() == State.STANDING) {
                System.out.println("Dealer stands.");
                this.dealer.outputHandWithTotal(this.dealer.getCurrentHand());
            }

            // Switch the dealer's hand if necessary.
            if (this.dealer.switchHand(this.deck)) {
                continue;
            }

            if (this.dealer.getState() == State.BUSTED) {
                if (this.dealer.getWhichHand() == WhichHand.SECOND) {
                    System.out.println("Dealer's second hand is busted.");
                } else {
                    System.out.println("Dealer's hand is busted.");
                    this.dealer.outputHandWithTotal(this.dealer.getCurrentHand());
                }
                break;
            } else if (this.dealer.getState() == State.STANDING) {
                break;
            }
        }
    }

    public void playWithConsole() {
        // Create players and deal initial cards.
        this.setupGame(false);
        // Check for a win from the initial cards.
        if (this.initialWin()) {
            return;
        }
        // Handle player and dealer splits if necessary.
        this.handleSplitting();

        this.playerTurn();
        if (this.player.getState() == State.BUSTED) {
            System.out.println("Dealer won!");
            return;
        } else if (this.player.getState() == State.WON) {
            System.out.println("Player won!");
            return;
        }

        this.dealerTurn();
        if (this.dealer.getState() == State.BUSTED) {
            System.out.println("Player won!");
            return;
        } else if (this.dealer.getState() == State.WON) {
            System.out.println("Dealer won!");
            return;
        }

        // Both are standing.
        this.determineStandingWinner();
    }

    public void playWithFile(String file) {
        if (file == null) {
            System.out.print("Path to file: ");
            this.choice = input.nextLine();
        } else {
            this.choice = file;
        }
        // this.choice = "/home/randy/Downloads/dealer_and_player_blackjack.txt";
        // this.choice = "/home/randy/Downloads/dealer_bust_player_wins.txt";
        // this.choice = "/home/randy/Downloads/player_and_dealer_stand_player_wins.txt";
        // this.choice = "/home/randy/Downloads/player_splits_and_wins.txt";
        // this.choice = "/home/randy/Downloads/dealer_splits_and_player_wins.txt";

        String contents = this.readFile(this.choice);
        if (contents.equals("error")) {
            return;
        }

        List<String> actions = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        if (!this.parseFile(contents, actions, cards)) {
            return;
        }

        this.deck = new Deck();
        this.deck.create(cards);

        // Create players and deal initial cards.
        this.setupGame(true);

        // Check for a win from the initial cards.
        if (this.initialWin()) {
            return;
        }

        this.playerTurnFile(actions);
        if (this.player.getState() == State.BUSTED) {
            System.out.println("Dealer won!");
            return;
        } else if (this.player.getState() == State.WON) {
            System.out.println("Player won!");
            return;
        }

        this.dealerTurn();
        if (this.dealer.getState() == State.BUSTED) {
            System.out.println("Player won!");
            return;
        } else if (this.dealer.getState() == State.WON) {
            System.out.println("Dealer won!");
            return;
        }   

        // Both are standing.
        this.determineStandingWinner();
    }

    @SuppressWarnings("resource")
    public String readFile(String choice) {
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new FileReader(this.choice));
        } catch (FileNotFoundException e) {
            System.out.println("File " + choice + " not found.");
            return "error";
        }
        try {
            line = br.readLine();
        } catch (IOException e) {
            return "error";
        }
        try {
            br.close();
        } catch (IOException e) {
            return "error";
        }

        return line;
    }

    public boolean parseFile(String contents, List<String> actions, List<Card> cards) {
        List<String> cardStrings = new ArrayList<>();

        for (String event : contents.split(" ")) {
            if (event.length() > 1) {
                cardStrings.add(event);
            } else {
                actions.add(event);
            }
        }

        // Check cards already played.
        Set<String> unique_cards = new HashSet<>(cardStrings);
        if (cardStrings.size() > unique_cards.size()) {
            System.out.println("Error: " + this.choice + " contains duplicate cards.");
        }

        for (String card : cardStrings) {
            boolean properSuit = false;
            boolean properRank = false;

            char suit = card.charAt(0);
            String rank = card.substring(1, card.length());

            for (char s : Globals.suites) {
                if (suit == s) {
                    properSuit = true;
                }
            }
            if (!properSuit) {
                System.out.println("Cards with incorrect suites detected.");
                return false;
            }

            for (String r : Globals.ranks) {
                if (rank.equals(r)) {
                    properRank = true;
                }
            }
            if (!properRank) {
                System.out.println("Cards with incorrect ranks detected.");
                return false;
            }

            cards.add(new Card(suit, rank));
        }

        return true;
    }

    private void playerTurnFile(List<String> actions) {
        int actionIndex = 0;
        // Handle player and dealer splits if necessary.
        if (actions.size() > 1 && actions.get(0).equals("D")) {
            System.out.println("Player is splitting.");
            this.player.split(this.deck);
            actionIndex = 1;
        }        
        this.dealer.split(this.deck);

        for (int i = actionIndex; i < actions.size(); i++) {
            if (actions.get(i).equals("H")) {
                this.player.hit(this.deck.draw());
            } else if (actions.get(i).equals("S")) {
                System.out.println("Player stands.");
                this.player.stand();
                if (this.player.switchHand(this.deck)) {
                    
                } else {
                    this.player.outputHandWithTotal(this.player.getCurrentHand());
                }
                actionIndex = i;
            }
        }
    }
}
