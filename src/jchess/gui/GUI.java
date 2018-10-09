package jchess.gui;

import jchess.game.board.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI extends JFrame {

    private JPanel boardPanel;
    private Board board;
    private JButton[][] tileButton = new JButton[Board.MAX_X_COORDINATE][Board.MAX_Y_COORDINATE];
    public GUI(String title, Board board) {

        super(title);
        this.board = board;
        initGUI();
//        setupGame();
    }
//
    private void initGUI() {

        setLayout(new BorderLayout());
//
//        Button testButton = new Button("Test");
//        Container c = getContentPane();
//        testButton.addActionListener(new tileButtonActionListener(this.board));
//        c.add(testButton, BorderLayout.PAGE_START);

        boardPanel = new JPanel(new GridLayout(0, 9));

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < tileButton.length; i++) {
            for (int j = 0; j < tileButton.length; j++) {

                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                // if both odd or bot =h even then white
                if ((j % 2 == 1 && i % 2 == 1)
                        || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.BLACK);
                } else {
                    b.setBackground(Color.WHITE);
                }

                tileButton[i][j] = b;
            }
        }
    }
//
//    private void setupGame() {
//
//    }
}
