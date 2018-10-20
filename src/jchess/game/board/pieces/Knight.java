package jchess.game.board.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

public class Knight extends Piece {

    private final int[] CANDIDATE_MOVE_X_COORDINATES = {-2, -1, 1, 2};
    private final int[] CANDIDATE_MOVE_Y_COORDINATES = {-2, -1, 1, 2};

    public Knight(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
    }

    @Override
    public void calculatePieceLegalMoves() {

        this.legalMoves.clear();

        for(final int xCoordinateOffset : CANDIDATE_MOVE_X_COORDINATES) {

            for (final int yCoordinateOffset : CANDIDATE_MOVE_Y_COORDINATES) {

                if((xCoordinateOffset == yCoordinateOffset) || (xCoordinateOffset == ((-1) * (yCoordinateOffset)))) {
                    continue;
                }

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
    public int getPieceValue() {
        return 300;
    }

    @Override
    public String toString() {

        Alliance pieceAlliance = getPieceAlliance();
        if(pieceAlliance.isWhite()) {
            return "N";
        }
        else
            return "n";
    }
}
