package com.manthanhd.algorithms.monopoly.orchestration;

import com.manthanhd.algorithms.monopoly.exception.BankruptPlayerException;
import com.manthanhd.algorithms.monopoly.model.Place;
import com.manthanhd.algorithms.monopoly.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manthanhd on 20/05/2016.
 */
public class Board {
    private final List<Place> places = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final List<Player> removedPlayers = new ArrayList<>();
    private int dieSize;

    public Board(int dieSize) {
        this.dieSize = dieSize;
        init();
    }

    private void init() {
        players.add(new Player(5000, "Car", 0.3));      // safe investor
        players.add(new Player(5000, "Shoe", 0.7));     // eager saver
        players.add(new Player(5000, "Hat", 0.5));      // regular saver
        players.add(new Player(5000, "Wagon", 0.1));    // eager investor

        final Place startingPlace = new Place("Start", -200);
        for(final Player player : players) {
            try {
                player.move(startingPlace);
            } catch (BankruptPlayerException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Player %s is currently at %s.", player.getName(), startingPlace.getName()));
        }
        places.add(startingPlace);

        places.add(new Place("Vine Street", 100));
        places.add(new Place("Coventry Street", 57));
        places.add(new Place("Leicester Square", 68));
        places.add(new Place("Bow Street", 71));
        places.add(new Place("Whitechapel Road", 81));
        // side 2
        places.add(new Place("The Angel, Islington", 91));
        places.add(new Place("Trafalgar Square", 97));
        places.add(new Place("Northumrl'd Avenue", 112));
        places.add(new Place("M'borough Street", 125));
        places.add(new Place("Fleet Street", 148));
        places.add(new Place("Old Kent Road", 208));
        // side 3
        places.add(new Place("Whitehall", 211));
        places.add(new Place("Pentolville Road", 215));
        places.add(new Place("Paul Mall", 228));
        places.add(new Place("Bond Street", 271));
        places.add(new Place("Strand", 320));
        places.add(new Place("Regent Street", 370));
        // side 4
        places.add(new Place("Euston Road", 404));
        places.add(new Place("Piccadilly", 440));
        places.add(new Place("Oxford Street", 550));
        places.add(new Place("Park Lane", 562));
        places.add(new Place("Mayfair", 1800));
    }

    public void playRound() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(String.format("It's %s's turn!", player.getName()));
            int roll = (int) (Math.random() * dieSize) + 1;
            System.out.println(String.format("Dice roll was %d", roll));
            int currentLocation = places.indexOf(player.getCurrentPlace());
            int targetLocation = roll + currentLocation;
            if (targetLocation >= places.size()) {
                targetLocation = targetLocation - places.size();
            }
            System.out.println(String.format("Player will move to %d", targetLocation));
            final Place targetPlace = places.get(targetLocation);
            try {
                player.move(targetPlace);
                if (targetPlace.isForSale() && player.canAffordToBuy(targetPlace)) {
                    targetPlace.buy(player);
                }
            } catch (BankruptPlayerException e) {
                System.out.println(String.format("Player %s has gone bankrupt. Removing from game.", player.getName()));
                declareBankruptcy(player);
            }
            System.out.println(String.format("Player has moved to %s", player.getCurrentPlace().getName()));
        }
    }

    private void declareBankruptcy(final Player bankruptPlayer) {
        for(Place place : places) {
            if(place.getOwner() == bankruptPlayer) {
                System.out.println(String.format("%s is now available for sale", place.getName()));
                place.removeOwner();
            }
        }

        players.remove(bankruptPlayer);
        removedPlayers.add(bankruptPlayer);
    }

    public void printRemainderMoney() {
        for(Player player : players) {
            System.out.println(String.format("%s player has %s money remaining", player.getName(), player.getMoney()));
        }

        for(Player player : removedPlayers) {
            System.out.println(String.format("%s player was removed from the game", player.getName()));
        }
    }
}
