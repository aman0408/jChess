package jchess.game.board;

import jchess.game.board.pieces.Piece;

public class Move {

    private int xCoordinatePrevious;
    private int yCoordinatePrevious;
    private int xCoordinateNew;
    private int yCoordinateNew;

    public Move(Piece movePiece, int xCoordinateNew, int yCoordinateNew) {
        this.xCoordinatePrevious = movePiece.getXCoordinate();
        this.yCoordinatePrevious = movePiece.getYCoordinate();
        this.xCoordinateNew = xCoordinateNew;
        this.yCoordinateNew = yCoordinateNew;
    }

    public static boolean isValidMove(Board board, Piece piece, int xCandidateDestinationCoordinate, int yCandidateDestinationCoordinate) {

        try {

            Piece pieceOnCoordinate = board.getPieceOnCoordinate(xCandidateDestinationCoordinate,
                                                    yCandidateDestinationCoordinate);

            if((pieceOnCoordinate).getPieceAlliance() == piece.getPieceAlliance()) {
                return false;
            } //else if(pieceOnCoordinate instanceof King) {
                //return false;
            //}

        } catch(NullPointerException e) {

            return true;
        }

        return true;
    }

    int getxCoordinatePrevious() {
        return xCoordinatePrevious;
    }

    int getyCoordinatePrevious() {
        return yCoordinatePrevious;
    }

    int getxCoordinateNew() {
        return xCoordinateNew;
    }

    int getyCoordinateNew() {
        return yCoordinateNew;
    }
}
