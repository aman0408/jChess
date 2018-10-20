package jchess.game.board.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    Board board;
    int xCoordinate;
    int yCoordinate;
    private Alliance pieceAlliance;
    int pieceMoveNumber;
    Set<Move> legalMoves = new HashSet<>();

    Piece(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        this.board = board;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.pieceAlliance = pieceAlliance;
        this.pieceMoveNumber = pieceMoveNumber;
    }

    public abstract void calculatePieceLegalMoves();

    public abstract String toString();

    public abstract int getPieceValue();

    public void setCoordinate(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public void setPieceMoveNumber() {
        this.pieceMoveNumber++;
    }

    public int getPieceMoveNumber() {
        return pieceMoveNumber;
    }

    public Set<Move> getPieceLegalMoves() {

        return this.legalMoves;
    }
}