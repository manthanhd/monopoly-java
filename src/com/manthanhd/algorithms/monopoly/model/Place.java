package com.manthanhd.algorithms.monopoly.model;

import com.manthanhd.algorithms.monopoly.exception.BankruptPlayerException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by manthanhd on 20/05/2016.
 */
public class Place {
    private final String name;
    private Player owner;
    private double cost;
    private final Set<Player> currentPlayers = new HashSet<>();

    public Place(String name, double cost) {
        this.cost = cost;
        this.name = name;
    }

    public void remove(final Player player) {
        currentPlayers.remove(player);
    }

    public void accept(final Player player) throws BankruptPlayerException {
        if (cost < 0) {
            player.collect(Math.abs(cost));
        }
        currentPlayers.add(player);

        if (owner != null && player != owner) {
            final double charge = player.charge(cost);
            owner.collect(charge);
            System.out.println(String.format("Player %s paid %.02f to player %s on %s", player.getName(), charge, owner.getName(), this.getName()));
            if (charge != cost) {
                owner.collect(player.getMoney());
                throw new BankruptPlayerException();
            }
        }
    }

    public boolean isForSale() {
        return owner == null && cost > 0;
    }

    public boolean buy(final Player owner) {
        if (this.owner == null) {
            final double charge = owner.charge(cost);
            if (charge == cost) {
                this.owner = owner;
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public Player getOwner() {
        return owner;
    }

    public void removeOwner() {
        owner = null;
    }
}
