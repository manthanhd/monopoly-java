package com.manthanhd.algorithms.monopoly.model;

import com.manthanhd.algorithms.monopoly.exception.BankruptPlayerException;

/**
 * Created by manthanhd on 20/05/2016.
 */
public class Player {
    private final String name;
    private double totalMoney = 0;
    private Place currentPlace;
    private double savingThreshold;

    public Player(final double startingAmount, final String name, final double savingThreshold) {
        totalMoney = startingAmount;
        this.name = name;
        this.savingThreshold = savingThreshold;
    }

    public void move(final Place targetPlace) throws BankruptPlayerException {
        if (currentPlace != null)
            currentPlace.remove(this);
        targetPlace.accept(this);
        currentPlace = targetPlace;
    }

    public double charge(final double cost) {
        final double remainder = totalMoney - cost;
        if (remainder < 0) {
            return totalMoney;
        }

        totalMoney = remainder;
        return cost;
    }

    public String getName() {
        return name;
    }

    public void collect(double money) {
        totalMoney += money;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public double getMoney() {
        return totalMoney;
    }

    public boolean canAffordToBuy(Place targetPlace) {
        final double affordance = totalMoney - targetPlace.getCost();
        if(affordance < 0) return false;

        return (affordance / totalMoney) > savingThreshold;
    }
}
