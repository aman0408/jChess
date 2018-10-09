package jchess.game.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

import java.util.Set;

public abstract class Piece {

    Board board;
    int xCoordinate;
    int yCoordinate;
    Alliance pieceAlliance;
    int pieceMoveNumber;
    Set<Move> legalMoves;

    Piece(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        this.board = board;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.pieceAlliance = pieceAlliance;
        this.pieceMoveNumber = pieceMoveNumber;
    }

    public abstract Alliance getPieceAlliance();
    public abstract void setPieceMoveNumber();
    public abstract int getPieceMoveNumber();
    public abstract void calculatePieceLegalMoves();
    public abstract String toString();
    public abstract void setCoordinate(int xCoordinate, int yCoordinate);
    public abstract int getXCoordinate();
    public abstract int getYCoordinate();
}