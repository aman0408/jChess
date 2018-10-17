package jchess.game.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

import java.util.Set;

public class King extends Piece {

    private final int[] CANDIDATE_MOVE_X_COORDINATES = {-1, 0, 1};
    private final int[] CANDIDATE_MOVE_Y_COORDINATES = {-1, 0, 1};

    public King(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
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
        return this.pieceMoveNumber;
    }

    @Override
    public Set<Move> getLegalMoves() {
        calculatePieceLegalMoves();
        return this.legalMoves;
    }

    @Override
    public void calculatePieceLegalMoves() {

        this.legalMoves.clear();

        for(final int xCoordinateOffset : CANDIDATE_MOVE_X_COORDINATES) {

            for (final int yCoordinateOffset : CANDIDATE_MOVE_Y_COORDINATES) {

                int xCandidateDestinationCoordinate = this.xCoordinate + xCoordinateOffset;
                int yCandidateDestinationCoordinate = this.yCoordinate + yCoordinateOffset;

                if(Board.isValidCoordinate(xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)
                        && Move.isValidMove(this.board, this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)) {

                    Move legalMove = new Move(this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate);
                    this.legalMoves.add(legalMove);
                }
            }
        }
    }

    @Override
    public String toString() {

        if(this.getPieceAlliance().isWhite()) {
            return "K";
        }
        else
            return "k";
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
