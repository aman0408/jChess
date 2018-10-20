package jchess.game.board.pieces;

import jchess.game.Alliance;
import jchess.game.board.Move;
import jchess.game.board.Board;

public class Rook extends Piece {

    private final int[] CANDIDATE_MOVE_X_VECTOR_COORDINATES = {-1, 0, 1};
    private final int[] CANDIDATE_MOVE_Y_VECTOR_COORDINATES = {-1, 0, 1};

    public Rook(Board board, int xCoordinate, int yCoordinate, Alliance pieceAlliance, int pieceMoveNumber) {
        super(board, xCoordinate, yCoordinate, pieceAlliance, pieceMoveNumber);
    }

    @Override
    public void calculatePieceLegalMoves() {

        this.legalMoves.clear();

        for(int xCoordinateOffset : CANDIDATE_MOVE_X_VECTOR_COORDINATES) {
            for (int yCoordinateOffset : CANDIDATE_MOVE_Y_VECTOR_COORDINATES) {

                if((xCoordinateOffset == yCoordinateOffset) || (xCoordinateOffset == ((-1) * yCoordinateOffset))) {

                    continue;
                }

                int xCandidateDestinationCoordinate = this.xCoordinate + xCoordinateOffset;
                int yCandidateDestinationCoordinate = this.yCoordinate + yCoordinateOffset;

                while(Board.isValidCoordinate(xCandidateDestinationCoordinate, yCandidateDestinationCoordinate)
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
        return 500;
    }

    @Override
    public String toString() {

        Alliance pieceAlliance = getPieceAlliance();
        if(pieceAlliance.isWhite()) {
            return "R";
        }
        else
            return "r";
    }
}
