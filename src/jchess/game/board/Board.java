package jchess.game.board;

import jchess.game.Alliance;
import jchess.game.board.pieces.*;
import jchess.gui.GUI;

import java.util.HashSet;
import java.util.Set;

import static jchess.game.Alliance.WHITE;

public class Board {

    private static final int MIN_X_COORDINATE = 1;
    public static final int  MAX_X_COORDINATE = 8;
    private static final int MIN_Y_COORDINATE = 1;
    public static final int MAX_Y_COORDINATE = 8;
    private Piece[][] boardCoordinate = new Piece[MAX_X_COORDINATE][MAX_Y_COORDINATE];
    private int xCoordinatePrevious;
    private int yCoordinatePrevious;
    private int xCoordinateNew;
    private int yCoordinateNew;
    private Alliance currentMoveAlliance;
    private boolean isMovePieceSelected;
    private Set<Piece> whitePieces = new HashSet<>();
    private Set<Piece> blackPieces = new HashSet<>();

    public Board() {

        initStandardBoard();
    }

    private Piece[][] getBoardCoordinate() {
        return boardCoordinate;
    }

    public Alliance getCurrentMoveAlliance() {
        return currentMoveAlliance;
    }

    private void setCurrentMoveAlliance(Alliance nextMoveAlliance) {
        this.currentMoveAlliance = nextMoveAlliance;
    }

    private Set<Piece> getAlliancePieces(Alliance alliance) {

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

        if(isInLegalMoves()) {

            System.out.println("Move made");
            setMovePieceSelected(false);
            updateBoard(gui);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isInLegalMoves() {

        Set<Piece> currentAlliancePieces = getAlliancePieces(currentMoveAlliance);
        for(Piece piece : currentAlliancePieces) {

            piece.calculatePieceLegalMoves();
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

    private void updateBoard(GUI gui) {

        Alliance oppositionAlliance = getOppositionAlliance(currentMoveAlliance);
        setCurrentMoveAlliance(oppositionAlliance);
        Piece movedPiece = getPieceOnCoordinate(xCoordinatePrevious, yCoordinatePrevious);
        boardCoordinate[xCoordinateNew - 1][yCoordinateNew - 1] =
                boardCoordinate[xCoordinatePrevious - 1][yCoordinatePrevious - 1];
        boardCoordinate[xCoordinatePrevious - 1][yCoordinatePrevious - 1] = null;
        movedPiece.setCoordinate(xCoordinateNew, yCoordinateNew);
        gui.updateBoardGUI();
        System.out.println("Updated board");
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

        System.out.println("Board initialised (BACKEND)");
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

    private Alliance getOppositionAlliance(Alliance alliance) {

        if(alliance == Alliance.WHITE) {
            return Alliance.BLACK;
        } else {
            return Alliance.WHITE;
        }
    }
}
