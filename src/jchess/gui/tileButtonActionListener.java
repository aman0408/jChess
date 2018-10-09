package jchess.gui;

import jchess.game.board.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tileButtonActionListener implements ActionListener {

    private Board board;

    tileButtonActionListener(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        board.makeMove(1, 1, 2, 2);
    }
}
