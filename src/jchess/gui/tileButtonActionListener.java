package jchess.gui;

import jchess.game.board.Board;
import jchess.game.board.pieces.Piece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tileButtonActionListener implements ActionListener {

    private GUI gui;
    private Board board;
    private int xCoordinate;
    private int yCoordinate;

    tileButtonActionListener(GUI gui, Board board, int xCoordinate, int yCoordinate) {
        this.gui = gui;
        this.board = board;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!board.isGameOver()) {

            Piece pieceOnCoordinate = board.getPieceOnCoordinate(xCoordinate, yCoordinate);

            if (pieceOnCoordinate == null || pieceOnCoordinate.getPieceAlliance() != board.getCurrentMoveAlliance()) {

                if (board.isMovePieceSelected()) {

                    board.setxCoordinateNew(xCoordinate);
                    board.setyCoordinateNew(yCoordinate);
                    if (board.makeMove(gui)) {
                        board.updateBoard(gui);
                    } else {
                        System.out.println("Invalid move!");
                    }
                }
            } else {

                board.setxCoordinatePrevious(xCoordinate);
                board.setyCoordinatePrevious(yCoordinate);
                board.setMovePieceSelected(true);
            }
        }
    }
}
