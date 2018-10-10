package jchess;

import jchess.game.board.Board;
import jchess.gui.GUI;

import javax.swing.*;

public class jChess {

    public static void main(String[] args) {

        Board board = new Board();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame frame = new GUI("jChess", board);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.pack();
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
            }
        });
    }
}
