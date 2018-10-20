package jchess.game.board.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

public class Bishop extends Piece {

    private final int[] CANDIDATE_MOVE_X_VECTOR_COORDINATES = {-1, 1};
    private final int[] CANDIDATE_MOVE_Y_VECTOR_COORDINATES = {-1, 1};

    public Bishop(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
    }

    @Override
    public void calculatePieceLegalMoves() {

        this.legalMoves.clear();

        for(final int xCoordinateOffset : CANDIDATE_MOVE_X_VECTOR_COORDINATES) {

            for (final int yCoordinateOffset : CANDIDATE_MOVE_Y_VECTOR_COORDINATES) {

                int xCandidateDestinationCoordinate = this.xCoordinate + xCoordinateOffset;
                int yCandidateDestinationCoordinate = this.yCoordinate + yCoordinateOffset;

                while(Board.isValidCoordinate(xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)
                        && Move.isValidMove(this.board, this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)) {

                    Move legalMove = new Move(this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate);
                    this.legalMoves.add(legalMove);

                    Piece pieceOnCoordinate = board.getPieceOnCoordinate(xCandidateDestinationCoordinate,
                            yCandidateDestinationCoordinate);
                    if (pieceOnCoordinate != null) {
                        break;
                    }

                    xCandidateDestinationCoordinate += xCoordinateOffset;
                    yCandidateDestinationCoordinate += yCoordinateOffset;
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
            return "B";
        }
        else
            return "b";
    }
}