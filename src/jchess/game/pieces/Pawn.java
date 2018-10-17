package jchess.game.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

import java.util.Set;

public class Pawn extends Piece {

    private final int[] CANDIDATE_MOVE_X_COORDINATES = {0};
    private final int[] CANDIDATE_MOVE_Y_COORDINATES = {-2, -1, 1, 2};

    public Pawn(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
    }

    @Override
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    @Override
    public void setPieceMoveNumber() {
        pieceMoveNumber++;
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

        for(int xCoordinateOffset : CANDIDATE_MOVE_X_COORDINATES) {

            for (int yCoordinateOffset : CANDIDATE_MOVE_Y_COORDINATES) {

                if(yCoordinateOffset == 2 || yCoordinateOffset == -2) {

                    if((this.getPieceAlliance().getDirection() * 2) != (yCoordinateOffset)) {
                        continue;
                    }

                    if(pieceMoveNumber != 0)  {
                        continue;
                    }
                } else {

                    if((this.getPieceAlliance().getDirection()) != (yCoordinateOffset)) {
                        continue;
                    }
                }

                //kill move and en passent to be managed
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
            return "P";
        }
        else
            return "p";
    }

    @Override
    public void setCoordinate(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    @Override
    public int getYCoordinate() {
        return this.yCoordinate;
    }
}
