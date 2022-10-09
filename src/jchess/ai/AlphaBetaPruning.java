package jchess.ai;

import jchess.game.Alliance;
import jchess.game.board.Board;
import jchess.game.board.Move;
import jchess.game.board.pieces.Piece;

import java.util.Set;

public class AlphaBetaPruning implements MoveStrategy {

    @Override
    public Move execute(Board board, int depth) {

        Move bestMove = null;
        long startTime = System.currentTimeMillis();
        if(board.getCurrentMoveAlliance() == Alliance.WHITE) {

            int bestValue = Integer.MIN_VALUE;

            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;

            for(Piece piece : Set.copyOf(board.getAlliancePieces(Alliance.WHITE))) {
                piece.calculatePieceLegalMoves();
                Set<Move> pieceMoves = Set.copyOf(piece.getPieceLegalMoves());
                for(Move move : pieceMoves) {


                    Piece pieceOnMoveDestination =
                            board.getPieceOnCoordinate(move.getxCoordinateNew(), move.getyCoordinateNew());
                    updateAlphaBetaBoard(board, move);
                    int currentValue = miniMax(board, depth - 1, alpha, beta);
                    revertAlphaBetaBoard(board, move, pieceOnMoveDestination);

                    if (currentValue > bestValue) {
                        bestValue = currentValue;
                        bestMove = move;
                    }
                }
            }
        } else {

            int bestValue = Integer.MAX_VALUE;

            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            for(Piece piece : Set.copyOf(board.getAlliancePieces(Alliance.BLACK))) {
                piece.calculatePieceLegalMoves();
                for(final Move move : Set.copyOf(piece.getPieceLegalMoves())) {
                    Piece pieceOnMoveDestination =
                            board.getPieceOnCoordinate(move.getxCoordinateNew(), move.getyCoordinateNew());
                    updateAlphaBetaBoard(board, move);
                    int currentValue = miniMax(board, depth - 1, alpha, beta);
                    if (currentValue < bestValue) {

                        bestValue = currentValue;
                        bestMove = move;
                    }
                    revertAlphaBetaBoard(board, move, pieceOnMoveDestination);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Best move played, time elapsed: " + executionTime + " milliseconds");
        return bestMove;
    }

    private int miniMax(Board board, int depth, int alpha, int beta) {

        if(depth == 0 || board.isGameOver()) {
            if(board.isGameOver()) {
                board.setGameOver(false);
            }
            return BoardEvaluator.evaluate(board, depth);
        }

        if(board.getCurrentMoveAlliance() == Alliance.WHITE) {

            int bestValue = Integer.MIN_VALUE;

            for(Piece piece : Set.copyOf(board.getAlliancePieces(Alliance.WHITE))) {

                piece.calculatePieceLegalMoves();
                Set<Move> pieceMoves = Set.copyOf(piece.getPieceLegalMoves());
                for (Move move : pieceMoves) {

                    Piece pieceOnMoveDestination =
                            board.getPieceOnCoordinate(move.getxCoordinateNew(), move.getyCoordinateNew());
                    updateAlphaBetaBoard(board, move); // changes boardcurrentmovealliance
                    int currentValue = miniMax(board, depth - 1, alpha, beta);
                    revertAlphaBetaBoard(board, move, pieceOnMoveDestination);

                    bestValue = Math.max(bestValue, currentValue);
                    alpha = Math.max(alpha, bestValue);

                    if(beta <= alpha) {
                        break;
                    }
                }
            }

            return bestValue;
        } else {

            int bestValue = Integer.MAX_VALUE;
            for(Piece piece : Set.copyOf(board.getAlliancePieces(Alliance.BLACK))) {

                piece.calculatePieceLegalMoves();
                Set<Move> pieceMoves = Set.copyOf(piece.getPieceLegalMoves());
                for (Move move : pieceMoves) {

                    Piece pieceOnMoveDestination =
                            board.getPieceOnCoordinate(move.getxCoordinateNew(), move.getyCoordinateNew());
                    updateAlphaBetaBoard(board, move); // changes boardcurrentmovealliance
                    int currentValue = miniMax(board, depth - 1, alpha, beta);
                    revertAlphaBetaBoard(board, move, pieceOnMoveDestination);

                    bestValue = Math.min(bestValue, currentValue);
                    beta = Math.min(beta, bestValue);

                    if(beta <= alpha) {
                        break;
                    }
                }
            }

            return bestValue;
        }
    }

    private void updateAlphaBetaBoard(Board board, Move AIMove) {

        // update boardCoordinate
        // update movedPiece coordinates + setmovenumber
        // change current move alliance
        // update new current move alliance pieces
        // calculate their legal moves

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

    private void revertAlphaBetaBoard(Board board, Move AIMove, Piece pieceOnPrevDestination) {
        Alliance oppositionAlliance = board.getOppositionAlliance(board.getCurrentMoveAlliance());
        board.setCurrentMoveAlliance(oppositionAlliance);
        board.updateAlliancePieces();
        Piece movedPiece = AIMove.getMovedPiece();
        Piece[][] boardCoordinate = board.getBoardCoordinate();
        boardCoordinate[board.getxCoordinatePrevious() - 1][board.getyCoordinatePrevious() - 1] =
                boardCoordinate[board.getxCoordinateNew() - 1][board.getyCoordinateNew() - 1];
        boardCoordinate[board.getxCoordinateNew() - 1][board.getyCoordinateNew() - 1] = pieceOnPrevDestination;
        movedPiece.setCoordinate(board.getxCoordinatePrevious(), board.getyCoordinatePrevious());
        board.updateAlliancePieces();
    }
}
