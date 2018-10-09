package jchess.game.board;

import jchess.game.Alliance;
import jchess.game.pieces.*;

import java.util.HashSet;
import java.util.Set;

import static jchess.game.Alliance.WHITE;

public class Board {

    //public static final int NUMBER_OF_TILES = 64;
    public static final int MIN_X_COORDINATE = 1;
    public static final int  MAX_X_COORDINATE = 8;
    public static final int MIN_Y_COORDINATE = 1;
    public static final int MAX_Y_COORDINATE = 8;
    private Piece[][] boardCoordinate = new Piece[MAX_X_COORDINATE][MAX_Y_COORDINATE];
    private Set<Piece> whitePieces = new HashSet<>();
    private Set<Piece> blackPieces = new HashSet<>();

    public Board() {

        initStandardBoard();
    }

    public static boolean isValidCoordinate(int xCandidateDestinationCoordinate, int yCandidateDestinationCoordinate) {

        return ((xCandidateDestinationCoordinate >= MIN_X_COORDINATE)
                && (xCandidateDestinationCoordinate <= MAX_Y_COORDINATE)
                && (yCandidateDestinationCoordinate >= MIN_X_COORDINATE)
                && (yCandidateDestinationCoordinate <= MAX_Y_COORDINATE));
    }

    public void makeMove(int xCoordinatePrevious, int yCoordinatePrevious,
                         int xCoordinateNext, int yCoordinateNext) {

        System.out.println("Previous = (" + xCoordinatePrevious + "," + yCoordinatePrevious + ")");
        System.out.println("Next = (" + xCoordinateNext + "," + yCoordinateNext + ")");
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

        for(int y = MAX_Y_COORDINATE; y >= MIN_Y_COORDINATE; y--) {

            for(int x = MIN_X_COORDINATE; x <= MAX_X_COORDINATE; x++) {

                System.out.print(((this.boardCoordinate[x - 1][y - 1] == null) ?
                        "-" : (this.boardCoordinate[x - 1][y - 1].toString())) + "  ");
            }

            System.out.println();
        }
    }

    public Piece getPieceOnCoordinate(int xCoordinate, int yCoordinate) {

        return (boardCoordinate[xCoordinate - 1][yCoordinate - 1]);
    }
}
