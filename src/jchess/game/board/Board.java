package jchess.game.board;

import jchess.ai.BoardEvaluator;
import jchess.ai.MiniMax;
import jchess.game.Alliance;
import jchess.game.board.pieces.*;
import jchess.gui.GUI;
import jdk.dynalink.linker.GuardedInvocation;

import java.util.HashSet;
import java.util.Set;

import static jchess.game.Alliance.WHITE;

public class Board {

    private static final int MIN_X_COORDINATE = 1;
    public static final int  MAX_X_COORDINATE = 8;
    private static final int MIN_Y_COORDINATE = 1;
    public static final int MAX_Y_COORDINATE = 8;
    private int moveNumber = 1;
    private Piece[][] boardCoordinate = new Piece[MAX_X_COORDINATE][MAX_Y_COORDINATE];
    private int xCoordinatePrevious;
    private int yCoordinatePrevious;
    private int xCoordinateNew;
    private int yCoordinateNew;
    private Alliance currentMoveAlliance;
    private boolean isMovePieceSelected;
    private Set<Piece> whitePieces = new HashSet<>();
    private Set<Piece> blackPieces = new HashSet<>();
    private boolean isGameOver = false;

    public Board() {

        initStandardBoard();
    }

    public Alliance getCurrentMoveAlliance() {
        return this.currentMoveAlliance;
    }

    public void setCurrentMoveAlliance(Alliance nextMoveAlliance) {
        this.currentMoveAlliance = nextMoveAlliance;
    }

    public Set<Piece> getAlliancePieces(Alliance alliance) {

        if(alliance == Alliance.WHITE) {
            return whitePieces;
        } else {
            return blackPieces;
        }
    }

    public boolean isMovePieceSelected() {
        return isMovePieceSelected;
    }

    public void setMovePieceSelected(boolean movePieceSelected) {
        isMovePieceSelected = movePieceSelected;
    }

    public int getxCoordinatePrevious() {
        return xCoordinatePrevious;
    }

    public void setxCoordinatePrevious(int xCoordinatePrevious) {
        this.xCoordinatePrevious = xCoordinatePrevious;
    }

    public int getyCoordinatePrevious() {
        return yCoordinatePrevious;
    }

    public void setyCoordinatePrevious(int yCoordinatePrevious) {
        this.yCoordinatePrevious = yCoordinatePrevious;
    }

    public int getxCoordinateNew() {
        return xCoordinateNew;
    }

    public void setxCoordinateNew(int xCoordinateNew) {
        this.xCoordinateNew = xCoordinateNew;
    }

    public int getyCoordinateNew() {
        return yCoordinateNew;
    }

    public void setyCoordinateNew(int yCoordinateNew) {
        this.yCoordinateNew = yCoordinateNew;
    }

    public static boolean isValidCoordinate(int xCandidateDestinationCoordinate, int yCandidateDestinationCoordinate) {

        return ((xCandidateDestinationCoordinate >= MIN_X_COORDINATE)
                && (xCandidateDestinationCoordinate <= MAX_Y_COORDINATE)
                && (yCandidateDestinationCoordinate >= MIN_X_COORDINATE)
                && (yCandidateDestinationCoordinate <= MAX_Y_COORDINATE));
    }

    public boolean makeMove(GUI gui) {

        if(isInAllianceLegalMoves(currentMoveAlliance)) {

            setMovePieceSelected(false);
            updateBoard(gui);
            return true;
        }
        else {
            return false;
        }
    }

    public int calculateAllianceLegalMoves(Alliance alliance) {

        int numberOfAllianceLegalMoves = 0;
        Set<Piece> currentAlliancePieces = getAlliancePieces(alliance);
        for (Piece piece : currentAlliancePieces) {

            piece.calculatePieceLegalMoves();
            numberOfAllianceLegalMoves += piece.getPieceLegalMoves().size();
        }
        return numberOfAllianceLegalMoves;
    }

    private boolean isInAllianceLegalMoves(Alliance alliance) {

        Set<Piece> currentAlliancePieces = getAlliancePieces(alliance);
        for(Piece piece : currentAlliancePieces) {

            Set<Move> pieceLegalMoves = piece.getPieceLegalMoves();
            for(Move move : pieceLegalMoves) {

                if(move.getxCoordinatePrevious() == getxCoordinatePrevious()
                && move.getyCoordinatePrevious() == getyCoordinatePrevious()
                && move.getxCoordinateNew() == getxCoordinateNew()
                && move.getyCoordinateNew() == getyCoordinateNew()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void updateAlliancePieces() {


        Set<Piece> alliancePieces = getAlliancePieces(currentMoveAlliance);
        alliancePieces.clear();

        for(int x = 0; x < MAX_X_COORDINATE; x++) {
            for(int y = 0; y < MAX_Y_COORDINATE; y++) {

                Piece piece = boardCoordinate[x][y];

                if(piece != null) {
                    if(piece.getPieceAlliance() == currentMoveAlliance) {

                        alliancePieces.add(piece);
                    }
                }
            }
        }
    }

    private void updateBoard(GUI gui) {

        Alliance oppositionAlliance = getOppositionAlliance(currentMoveAlliance);
        setCurrentMoveAlliance(oppositionAlliance);

        Piece movedPiece = getPieceOnCoordinate(xCoordinatePrevious, yCoordinatePrevious);
        boardCoordinate[xCoordinateNew - 1][yCoordinateNew - 1] =
                boardCoordinate[xCoordinatePrevious - 1][yCoordinatePrevious - 1];
        boardCoordinate[xCoordinatePrevious - 1][yCoordinatePrevious - 1] = null;
        updateAlliancePieces();


        movedPiece.setCoordinate(xCoordinateNew, yCoordinateNew);
        movedPiece.setPieceMoveNumber();

        int numberOfPossibleMoves = calculateAllianceLegalMoves(currentMoveAlliance);
        gui.updateBoardGUI();

        printBoardStatus(numberOfPossibleMoves);
        if(numberOfPossibleMoves == 0) {

            isGameOver = true;
            gui.endGame();
            System.out.println("GAME OVER");
        }

        if(currentMoveAlliance == Alliance.BLACK && !isGameOver) {
            AIMove(gui);
        }
    }

    private void AIMove(GUI gui) {
        MiniMax minimax = new MiniMax();
            Move AIMove = minimax.execute(this, 1);
            xCoordinatePrevious = AIMove.getxCoordinatePrevious();
            yCoordinatePrevious = AIMove.getyCoordinatePrevious();
            xCoordinateNew = AIMove.getxCoordinateNew();
            yCoordinateNew = AIMove.getyCoordinateNew();
            updateBoard(gui);
    }

    private void printBoardStatus(int numberOfPossibleMoves) {
        System.out.println(getCurrentMoveAlliance() + "'s Move");
        System.out.println("Current board score: " + BoardEvaluator.evaluate(this, 0));
        System.out.println("Move number: " + (moveNumber++));
        System.out.println("Current alliance active pieces: " + getAlliancePieces(currentMoveAlliance).size());
        System.out.println("Possible moves: " + numberOfPossibleMoves + "\n");
    }

    private void initStandardBoard() {

        // WHITE
        boardCoordinate[0][0] = new Rook(this, 1, 1, WHITE, 0);
        boardCoordinate[1][0] = new Knight(this, 2, 1, WHITE, 0);
        boardCoordinate[2][0] = new Bishop(this, 3, 1, WHITE, 0);
        boardCoordinate[3][0] = new Queen(this, 4, 1, WHITE, 0);
        boardCoordinate[4][0] = new King(this, 5, 1, WHITE, 0);
        boardCoordinate[5][0] = new Bishop(this, 6, 1, WHITE, 0);
        boardCoordinate[6][0] = new Knight(this, 7, 1, WHITE, 0);
        boardCoordinate[7][0] = new Rook(this, 8, 1, WHITE, 0);
        for(int xCoordinate = MIN_X_COORDINATE; xCoordinate <= MAX_X_COORDINATE; xCoordinate++) {

            boardCoordinate[xCoordinate - 1][1] = new Pawn(this, xCoordinate, 2, WHITE, 0);
        }

        // BLACK
        boardCoordinate[0][7] = new Rook(this, 1, 8, Alliance.BLACK, 0);
        boardCoordinate[1][7] = new Knight(this, 2, 8, Alliance.BLACK, 0);
        boardCoordinate[2][7] = new Bishop(this, 3, 8, Alliance.BLACK, 0);
        boardCoordinate[3][7] = new Queen(this, 4, 8, Alliance.BLACK, 0);
        boardCoordinate[4][7] = new King(this, 5, 8, Alliance.BLACK, 0);
        boardCoordinate[5][7] = new Bishop(this, 6, 8, Alliance.BLACK, 0);
        boardCoordinate[6][7] = new Knight(this, 7, 8, Alliance.BLACK, 0);
        boardCoordinate[7][7] = new Rook(this, 8, 8, Alliance.BLACK, 0);
        for(int xCoordinate = MIN_X_COORDINATE; xCoordinate <= MAX_X_COORDINATE; xCoordinate++) {

            boardCoordinate[xCoordinate - 1][6] = new Pawn(this, xCoordinate, 7, Alliance.BLACK, 0);
        }

        // REMAINING TILES TO BE MAPPED TO NULL
        for(int xCoordinate = MIN_X_COORDINATE; xCoordinate <= MAX_X_COORDINATE; xCoordinate++) {
            for(int yCoordinate =  (MIN_Y_COORDINATE + 2); yCoordinate <= (MAX_Y_COORDINATE - 2); yCoordinate++) {

                boardCoordinate[xCoordinate - 1][yCoordinate - 1] = null;
            }
        }

        setCurrentMoveAlliance(Alliance.WHITE);
        setMovePieceSelected(false);
        setPieces();
        int numberOfPossibleMoves = calculateAllianceLegalMoves(currentMoveAlliance);
        printBoardStatus(numberOfPossibleMoves);
        printCurrentBoard();
    }

    private void setPieces() {

        for(int xCoordinate = MIN_X_COORDINATE; xCoordinate <= MAX_X_COORDINATE; xCoordinate++) {
            for(int yCoordinate = MIN_Y_COORDINATE; yCoordinate <= MAX_Y_COORDINATE; yCoordinate++) {

                if(boardCoordinate[xCoordinate - 1][yCoordinate - 1] == null) {
                    continue;
                }

                Piece alliancePiece = boardCoordinate[xCoordinate - 1][yCoordinate - 1];
                if(alliancePiece.getPieceAlliance() == WHITE) {
                    whitePieces.add(alliancePiece);
                } else {
                    blackPieces.add(alliancePiece);
                }
            }
        }
    }

    private void printCurrentBoard() {

//        System.out.println("Board initialised (BACKEND)");
//        for(int y = MAX_Y_COORDINATE; y >= MIN_Y_COORDINATE; y--) {
//
//            for(int x = MIN_X_COORDINATE; x <= MAX_X_COORDINATE; x++) {
//
//                System.out.print(((this.boardCoordinate[x - 1][y - 1] == null) ?
//                        "-" : (this.boardCoordinate[x - 1][y - 1].toString())) + "  ");
//            }
//
//            System.out.println();
//        }
    }

    public Piece getPieceOnCoordinate(int xCoordinate, int yCoordinate) {

        return (boardCoordinate[xCoordinate - 1][yCoordinate - 1]);
    }

    public Alliance getOppositionAlliance(Alliance alliance) {

        if(alliance == Alliance.WHITE) {
            return Alliance.BLACK;
        } else {
            return Alliance.WHITE;
        }
    }

    public boolean isInCheckAfterMove(Piece piece, int xCandidateDestinationCoordinate,
                                      int yCandidateDestinationCoordinate) {

        Piece pieceOnDestination = getPieceOnCoordinate(xCandidateDestinationCoordinate,
                yCandidateDestinationCoordinate);
        if(pieceOnDestination != null) {
            getAlliancePieces(getOppositionAlliance(currentMoveAlliance)).remove(pieceOnDestination);
        }

        int xPrevious = piece.getXCoordinate();
        int yPrevious = piece.getYCoordinate();
        boardCoordinate[xCandidateDestinationCoordinate - 1][yCandidateDestinationCoordinate - 1] =
                boardCoordinate[xPrevious - 1][yPrevious - 1];
        boardCoordinate[xPrevious - 1][yPrevious - 1] = null;
        boolean isInCheck = isInCheck();

        // revert board
        boardCoordinate[xPrevious - 1][yPrevious - 1] =
                boardCoordinate[xCandidateDestinationCoordinate - 1][yCandidateDestinationCoordinate - 1];
        boardCoordinate[xCandidateDestinationCoordinate - 1][yCandidateDestinationCoordinate - 1] = pieceOnDestination;
        if(pieceOnDestination != null) {
            getAlliancePieces(getOppositionAlliance(currentMoveAlliance)).add(pieceOnDestination);
        }

        return isInCheck;
    }

    private boolean isInCheck() {

        Set<Piece> oppAlliancePieces = getAlliancePieces(getOppositionAlliance(currentMoveAlliance));
        for(Piece oppPiece : oppAlliancePieces) {
            oppPiece.calculatePieceLegalMoves();
            for(Move oppMove : oppPiece.getPieceLegalMoves()) {

                Piece pieceOnCoordinate = getPieceOnCoordinate(oppMove.getxCoordinateNew(),
                                                               oppMove.getyCoordinateNew());

                if(pieceOnCoordinate instanceof King) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Piece[][] getBoardCoordinate() {
        return boardCoordinate;
    }
}
