package jchess.game.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

public class Queen extends Piece {

    private final int[] CANDIDATE_MOVE_X_VECTOR_COORDINATES = {-1, 0, 1};
    private final int[] CANDIDATE_MOVE_Y_VECTOR_COORDINATES = {-1, 0, 1};

    public Queen(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
    }

    @Override
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    @Override
    public void setPieceMoveNumber() {
        this.pieceMoveNumber++;
    }

    @Override
    public int getPieceMoveNumber() {
        return pieceMoveNumber;
    }

    @Override
    public void calculatePieceLegalMoves() {

        this.legalMoves.clear();

        for(final int xCoordinateOffset : CANDIDATE_MOVE_X_VECTOR_COORDINATES) {

            for (final int yCoordinateOffset : CANDIDATE_MOVE_Y_VECTOR_COORDINATES) {

                int xCandidateDestinationCoordinate = this.xCoordinate + xCoordinateOffset;
                int yCandidateDestinationCoordinate = this.yCoordinate + yCoordinateOffset;

                while (Board.isValidCoordinate(xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)
                        && Move.isValidMove(this.board, this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)) {

                    this.legalMoves.add(new Move(this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate));


                    if ((this.board).getPieceOnCoordinate(xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)
                            != null) {
                        break;
                    }

                    xCandidateDestinationCoordinate += xCoordinateOffset;
                    yCandidateDestinationCoordinate += yCoordinateOffset;

                }
            }
        }
    }

    @Override
    public String toString() {

        if(this.getPieceAlliance().isWhite()) {
            return "Q";
        }
        else
            return "q";
    }

    @Override
    public void setCoordinate(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public int getXCoordinate() {
        return xCoordinate;
    }

    @Override
    public int getYCoordinate() {
        return yCoordinate;
    }
}