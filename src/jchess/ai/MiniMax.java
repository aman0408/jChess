package jchess.ai;

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

        final long startTime = System.currentTimeMillis();
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

            // update board
            currentValue = board.getCurrentMoveAlliance().isWhite() ?
                    min(board, depth - 1) : max(board, depth - 1);

            if(board.getCurrentMoveAlliance().isWhite() && currentValue > highestSeenValue) {

                highestSeenValue = currentValue;
                bestMove = move;
            } else if((!board.getCurrentMoveAlliance().isWhite()) && currentValue < lowestSeenValue) {
                lowestSeenValue = currentValue;
                bestMove = move;
            }
            // revert board
        }

        final long endTime = System.currentTimeMillis();
        final long executionTime = endTime - startTime;
        System.out.println("Best move found, time elapsed: " + executionTime);
        return bestMove;
    }

    private int min(Board board, int depth) {

        if(depth == 0 || board.isGameOver()) {
            return BoardEvaluator.evaluate(board, depth);
        }

        Set<Move> allianceLegalMoves = new HashSet<>();
        for(Piece piece : board.getAlliancePieces(board.getCurrentMoveAlliance())) {
            allianceLegalMoves.addAll(piece.getPieceLegalMoves());
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for(Move move : allianceLegalMoves) {

            // update board
            int currentValue = max(board, depth - 1);
            if(currentValue < lowestSeenValue) {
                lowestSeenValue = currentValue;
            }
            // revert board
        }

        return lowestSeenValue;
    }

    private int max(Board board, int depth) {

        if(depth == 0 || board.isGameOver()) {
            return BoardEvaluator.evaluate(board, depth);
        }

        Set<Move> allianceLegalMoves = new HashSet<>();
        for(Piece piece : board.getAlliancePieces(board.getCurrentMoveAlliance())) {
            allianceLegalMoves.addAll(piece.getPieceLegalMoves());
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for(Move move : allianceLegalMoves) {

            //update board
            //--
            int currentValue = min(board, depth - 1);
            if(currentValue > highestSeenValue ) {
                highestSeenValue = currentValue;
            }
            // revert board
            // --
        }

        return highestSeenValue;
    }
}
