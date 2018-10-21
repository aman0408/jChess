package jchess.ai;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.pieces.Piece;

import java.util.Set;

public class BoardEvaluator {
    public static int evaluate(Board board, int depth) {

        return playerScore(board, Alliance.WHITE, depth) -
                playerScore(board, Alliance.BLACK, depth);
    }

    private static int playerScore(Board board, Alliance alliance, int depth) {

        return playerPiecesValue(board, alliance) + mobility(board, alliance, depth);
    }

    private static int playerPiecesValue(Board board, Alliance alliance) {

        int totalPieceScore = 0;
        for(Piece piece : board.getAlliancePieces(alliance)) {
            totalPieceScore += piece.getPieceValue();
        }
        return totalPieceScore;
    }

    private static int mobility(Board board, Alliance alliance, int depth) {

        int selfTotalMobility = 0;
        Set<Piece> selfAlliancePieces = board.getAlliancePieces(alliance);

        for(Piece piece : selfAlliancePieces) {
            selfTotalMobility += piece.getPieceLegalMoves().size();
        }

        if(selfTotalMobility == 0) { // case of checkmate
            return (-1 * (1000 * (depth + 1)));
        } else if(selfTotalMobility < 6) {
            return (-50 + (selfTotalMobility * 10));
        }

        return (selfTotalMobility * 10);
    }
}
