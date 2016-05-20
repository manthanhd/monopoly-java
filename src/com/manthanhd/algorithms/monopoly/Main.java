package com.manthanhd.algorithms.monopoly;

import com.manthanhd.algorithms.monopoly.orchestration.Board;

public class Main {

    public static void main(String[] args) {
        final Board board = new Board(12);
        final int totalRounds = 100;
        for(int i = 0; i < totalRounds; i++) {
            board.playRound();
        }

        board.printRemainderMoney();
    }
}
