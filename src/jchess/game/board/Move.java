package jchess.game.board;

import jchess.game.board.pieces.Piece;

public class Move {

    private Piece movedPiece;
    private int xCoordinatePrevious;
    private int yCoordinatePrevious;
    private int xCoordinateNew;
    private int yCoordinateNew;

    public Move(Piece movePiece, int xCoordinateNew, int yCoordinateNew) {
        this.movedPiece = movePiece;
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

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public int getxCoordinatePrevious() {
        return xCoordinatePrevious;
    }

    public int getyCoordinatePrevious() {
        return yCoordinatePrevious;
    }

    public int getxCoordinateNew() {
        return xCoordinateNew;
    }

    public int getyCoordinateNew() {
        return yCoordinateNew;
    }
}
