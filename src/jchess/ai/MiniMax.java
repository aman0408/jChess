package jchess.ai;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;
import jchess.game.board.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

public class MiniMax {


    @Override
    public String toString() {
        return "MiniMax";
    }

    public Move execute(Board board, int depth) {

        long startTime = System.currentTimeMillis();
        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.getCurrentMoveAlliance() + " thinking with depth " + depth + "...");

        Set<Move> allianceLegalMoves = new HashSet<>();
        for(Piece piece : board.getAlliancePieces(board.getCurrentMoveAlliance())) {
            allianceLegalMoves.addAll(piece.getPieceLegalMoves());
        }

        for(Move move : allianceLegalMoves) {

            Piece pieceOnDestinationCoordinate = board.getPieceOnCoordinate(move.getxCoordinateNew(), move.getyCoordinateNew());
            updateAIBoard(board, move);
            currentValue = board.getCurrentMoveAlliance().isWhite() ?
                    min(board, depth - 1) : max(board, depth - 1);

            if(!board.getCurrentMoveAlliance().isWhite() && currentValue >= highestSeenValue) {

                highestSeenValue = currentValue;
                bestMove = move;
            } else if((board.getCurrentMoveAlliance().isWhite()) && currentValue <= lowestSeenValue) {
                lowestSeenValue = currentValue;
                bestMove = move;
            }
            revertAIBoard(board, move, pieceOnDestinationCoordinate);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Best move played, time elapsed: " + executionTime + "ms");
        return bestMove;
    }

    private int min(Board board, int depth) {

        if(depth <= 0 || board.isGameOver()) {
            board.setGameOver(false);
            return BoardEvaluator.evaluate(board, depth);
        }

        Set<Move> allianceLegalMoves = new HashSet<>();
        for(Piece piece : board.getAlliancePieces(board.getCurrentMoveAlliance())) {
            allianceLegalMoves.addAll(piece.getPieceLegalMoves());
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for(Move move : allianceLegalMoves) {

            Piece pieceOnDestinationCoordinate = board.getPieceOnCoordinate(move.getxCoordinateNew(),
                    move.getyCoordinateNew());
            updateAIBoard(board, move);
            int currentValue = max(board, depth - 1);
            if(currentValue <= lowestSeenValue) {
                lowestSeenValue = currentValue;
            }
            revertAIBoard(board, move, pieceOnDestinationCoordinate);
        }

        return lowestSeenValue;
    }

    private int max(Board board, int depth) {

        if(depth <= 0 || board.isGameOver()) {
            board.setGameOver(false);
            return BoardEvaluator.evaluate(board, depth);
        }

        Set<Move> allianceLegalMoves = new HashSet<>();
        for(Piece piece : board.getAlliancePieces(board.getCurrentMoveAlliance())) {
            allianceLegalMoves.addAll(piece.getPieceLegalMoves());
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for(Move move : allianceLegalMoves) {

            Piece pieceOnDestinationCoordinate = board.getPieceOnCoordinate(move.getxCoordinateNew(),
                    move.getyCoordinateNew());
            updateAIBoard(board, move);
            int currentValue = min(board, depth - 1);
            if(currentValue >= highestSeenValue ) {
                highestSeenValue = currentValue;
            }
            revertAIBoard(board, move, pieceOnDestinationCoordinate);
        }

        return highestSeenValue;
    }

    private void updateAIBoard(Board board, Move AIMove) {

        Piece movedPiece = AIMove.getMovedPiece();
        board.setxCoordinatePrevious(AIMove.getxCoordinatePrevious());
        board.setyCoordinatePrevious(AIMove.getyCoordinatePrevious());
        board.setxCoordinateNew(AIMove.getxCoordinateNew());
        board.setyCoordinateNew(AIMove.getyCoordinateNew());
        Alliance oppositionAlliance = board.getOppositionAlliance(board.getCurrentMoveAlliance());
        board.setCurrentMoveAlliance(oppositionAlliance);
        Piece[][] boardCoordinate = board.getBoardCoordinate();

        boardCoordinate[board.getxCoordinateNew() - 1][board.getyCoordinateNew() - 1] =
                boardCoordinate[board.getxCoordinatePrevious() - 1][board.getyCoordinatePrevious() - 1];
        boardCoordinate[board.getxCoordinatePrevious() - 1][board.getyCoordinatePrevious() - 1] = null;
        board.updateAlliancePieces();
        movedPiece.setCoordinate(board.getxCoordinateNew(), board.getyCoordinateNew());
        int numberOfPossibleMoves = board.calculateAllianceLegalMoves(board.getCurrentMoveAlliance());
        if(numberOfPossibleMoves == 0) {
            board.setGameOver(true);
        }
    }

    private void revertAIBoard(Board board, Move AIMove, Piece pieceOnPrevDestination) {

        Alliance oppositionAlliance = board.getOppositionAlliance(board.getCurrentMoveAlliance());
        board.setCurrentMoveAlliance(oppositionAlliance);
        board.updateAlliancePieces(); // ???
        Piece movedPiece = AIMove.getMovedPiece();
        Piece[][] boardCoordinate = board.getBoardCoordinate();
        boardCoordinate[board.getxCoordinatePrevious() - 1][board.getyCoordinatePrevious() - 1] =
                boardCoordinate[board.getxCoordinateNew() - 1][board.getyCoordinateNew() - 1];
        boardCoordinate[board.getxCoordinateNew() - 1][board.getyCoordinateNew() - 1] = pieceOnPrevDestination;
        movedPiece.setCoordinate(board.getxCoordinatePrevious(), board.getyCoordinatePrevious());
        board.updateAlliancePieces();
    }
}
