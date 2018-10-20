package jchess.game.board.pieces;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;

public class Pawn extends Piece {

    private final int[] CANDIDATE_MOVE_X_COORDINATES = {0};
    private final int[] CANDIDATE_MOVE_Y_COORDINATES = {-2, -1, 1, 2};

    public Pawn(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
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

                    if(getPieceMoveNumber() != 0)  {
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

                    Alliance pieceAlliance = getPieceAlliance();
                    if(pieceAlliance == board.getCurrentMoveAlliance()) {

                        boolean isInCheckAfterMove = board.isInCheckAfterMove(this, xCandidateDestinationCoordinate,
                                yCandidateDestinationCoordinate);

                        if(!isInCheckAfterMove) {

                            if(board.getPieceOnCoordinate(xCandidateDestinationCoordinate,
                                    yCandidateDestinationCoordinate) instanceof King) {
                                break;
                            }
                            Move legalMove = new Move(this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate);
                            this.legalMoves.add(legalMove);
                        }
                    } else if(pieceAlliance != board.getCurrentMoveAlliance()) {

                        Move legalMove = new Move(this, xCandidateDestinationCoordinate, yCandidateDestinationCoordinate);
                        this.legalMoves.add(legalMove);
                        //System.out.println("TEST909");
                    }
                }
            }
        }
    }

    @Override
    public int getPieceValue() {
        return 100;
    }

    @Override
    public String toString() {

        Alliance pieceAlliance = getPieceAlliance();
        if(pieceAlliance.isWhite()) {
            return "P";
        }
        else
            return "p";
    }
}
