package jchess.gui;

import jchess.game.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GUI extends JFrame {

    private Board board;
    private JButton[][] tileButton = new JButton[Board.MAX_X_COORDINATE][Board.MAX_Y_COORDINATE];
    private Image[][] tileButtonIcons = new Image[2][6]; // 6 piece icons for each alliance

    public GUI(String title, Board board) {

        super(title);
        this.board = board;
        initGUI();
    }

    private void initGUI() {

        // Set Layout
        setLayout(new GridLayout(9, 9, 0, 0));
        setBackground(Color.GRAY);

        // Get Piece icons
        try {

            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage icons = ImageIO.read(url);

            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    tileButtonIcons[ii][jj] = icons.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {

            System.out.println("Error loading piece icons");
        }

        // Create buttons
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < tileButton.length; i++) {
            for (int j = 0; j < tileButton.length; j++) {

                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                // if both odd or both even then black
                if ((j % 2 == 1 && i % 2 == 1)
                        || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.BLACK);
                } else {
                    b.setBackground(Color.WHITE);
                }

                b.addActionListener(new tileButtonActionListener(this, board, i + 1, j + 1));
                tileButton[i][j] = b;
            }
        }

        // Set icons to piece buttons
        int[] pieceRow = {2, 3, 4, 1, 0, 4, 3, 2};
        for (int i = 0; i < Board.MAX_X_COORDINATE; i++) {
            tileButton[i][7].setIcon(new ImageIcon(
                    tileButtonIcons[0][pieceRow[i]])); // Setting black pieces
        }
        for (int i = 0; i < Board.MAX_X_COORDINATE; i++) {
            tileButton[i][6].setIcon(new ImageIcon(
                    tileButtonIcons[0][5])); // Setting black pawns
        }
        // set up the white pieces
        for (int i = 0; i < Board.MAX_X_COORDINATE; i++) {
            tileButton[i][1].setIcon(new ImageIcon(
                    tileButtonIcons[1][5])); // Setting pawns
        }
        for (int i = 0; i < Board.MAX_X_COORDINATE; i++) {
            tileButton[i][0].setIcon(new ImageIcon(
                    tileButtonIcons[1][pieceRow[i]]));
        }

        // Add buttons to board
        setupBoard();
    }

    private void setupBoard() {

        // Add buttons to board
        for(int y = 8; y > 0; y--) {
            for(int x = 0; x < 9; x++) {

                if(x == 0) {

                    add(new JLabel(Integer.toString(y), SwingConstants.CENTER));
                } else {

                    add(tileButton[x - 1][y - 1]);
                }

            }
        }

        String[] COLS = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for(int x = 0; x < 9; x++) {
            if(x == 0) {
                add(new JLabel(""));
            } else {

                add(new JLabel(COLS[x - 1], SwingConstants.CENTER));
            }
        }
    }

    void updateBoardGUI() {

        // get all the coordinates locally
        int a = board.getxCoordinatePrevious() - 1;
        int b = board.getyCoordinatePrevious() - 1;
        int c = board.getxCoordinateNew() - 1;
        int d = board.getyCoordinateNew() - 1;

        // set the movetileicon same as previoustileicon
        // set the previous tile icon to transparent
        Icon icon = tileButton[a][b].getIcon();
        tileButton[c][d].setIcon(icon);
        tileButton[a][b].setIcon(new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)));

    }
}
