package de.tesa_klebeband.connect4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Connect4Gui {
    final int WINDOW_WIDTH = 640;
    final int WINDOW_HEIGHT = 480;
    final int SETTINGS_WINDOW_WIDTH = 480;
    final int SETTINGS_WINDOW_HEIGHT = 320;

    private JFrame gameFrame;
    private JFrame settingsFrame;
    private Connect4 game;
    private int key = 0;
    private int currentPlayer = 1;
    private int column = 1;
    private String frameDisplayString = "Player 1's turn";
    private int[] wins = new int[2];
    private Color[] playerColors = {Color.RED, Color.BLUE};
    private boolean blockInput = false;

    public void initGame() {
        gameFrame = new JFrame("Connect4J");
        JPanel gamePanel = new GPanel();
        game = new Connect4();
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if (key == 0) {
                    key = keyEvent.getKeyCode();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                key = 0;
            }
        };

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameFrame.setResizable(true);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(gamePanel);
        gameFrame.addKeyListener(keyListener);
        gameFrame.setVisible(true);

        game.resetGame();
    }

    public void initSettings() {
        // Settings window
        settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(SETTINGS_WINDOW_WIDTH, SETTINGS_WINDOW_HEIGHT);
        settingsFrame.setResizable(true);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Settings components
        JPanel settingsPanel = new JPanel();
        JPanel gameSizePanel = new JPanel();
        JPanel colorPanelContainer = new JPanel();
        JPanel[] playerColorPanels = {new JPanel(), new JPanel()};
        JButton resetButton = new JButton("Reset Score");
        JButton applySizeButton = new JButton("Apply Size");
        JButton[][] colorButtons = new JButton[2][9];
        JTextField columnField = new JTextField();
        JTextField rowField = new JTextField();
        JLabel columnLabel = new JLabel("Number of columns:");
        JLabel rowLabel = new JLabel("Number of rows:");

        // Color mappings for the buttons
        String[] colorMappingStrings = {"Red", "Green", "Blue", "Yellow", "Orange", "Pink", "Cyan", "Magenta", "Gray"};
        Color[] colorMappings = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA, Color.GRAY};

        // Set layouts
        settingsPanel.setLayout(new GridLayout(2, 1));
        colorPanelContainer.setLayout(new GridLayout(1, 2));
        gameSizePanel.setLayout(new GridLayout(3, 2));
        playerColorPanels[0].setLayout(new GridLayout(3, 3));
        playerColorPanels[1].setLayout(new GridLayout(3, 3));

        // Set background colors and borders
        //Set default background using UIManager
        settingsPanel.setBackground(Color.DARK_GRAY);
        gameSizePanel.setBackground(Color.DARK_GRAY);
        colorPanelContainer.setBackground(Color.DARK_GRAY);
        playerColorPanels[0].setBackground(Color.DARK_GRAY);
        playerColorPanels[1].setBackground(Color.DARK_GRAY);
        rowField.setBackground(Color.DARK_GRAY);
        columnField.setBackground(Color.DARK_GRAY);
        rowField.setForeground(Color.WHITE);
        columnField.setForeground(Color.WHITE);
        rowLabel.setForeground(Color.WHITE);
        columnLabel.setForeground(Color.WHITE);
        rowLabel.setBackground(Color.DARK_GRAY);
        columnLabel.setBackground(Color.DARK_GRAY);
        rowField.setCaretColor(Color.WHITE);
        columnField.setCaretColor(Color.WHITE);
        applySizeButton.setBackground(Color.GRAY);
        applySizeButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.GRAY);
        resetButton.setForeground(Color.WHITE);
        settingsPanel.setBorder(null);
        gameSizePanel.setBorder(null);
        colorPanelContainer.setBorder(null);
        playerColorPanels[0].setBorder(null);
        playerColorPanels[1].setBorder(null);
        rowLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        columnLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        rowField.setBorder(null);
        columnField.setBorder(null);
        applySizeButton.setBorder(null);
        resetButton.setBorder(null);

        // Top settings panel - game size
        columnField.setText(game.getWidth() + "");
        rowField.setText(game.getHeight() + "");

        applySizeButton.addActionListener(actionEvent -> {
            try {
                int columns = Integer.parseInt(columnField.getText());
                if (columns < 4) {
                    columnLabel.setForeground(Color.RED);
                } else {
                    columnLabel.setForeground(Color.WHITE);
                    game.setColumns(columns);
                    game.resetGame();
                }
            } catch (NumberFormatException e) {
                columnLabel.setForeground(Color.RED);
            }
            try {
                int rows = Integer.parseInt(rowField.getText());
                if (rows < 4) {
                    rowLabel.setForeground(Color.RED);
                } else {
                    rowLabel.setForeground(Color.WHITE);
                    game.setRows(rows);
                    game.resetGame();
                }
            } catch (NumberFormatException e) {
                rowLabel.setForeground(Color.RED);
            }
        });

        resetButton.addActionListener(actionEvent -> {
            wins[0] = 0;
            wins[1] = 0;
            game.resetGame();
        });

        // Add components to game size panel
        gameSizePanel.add(columnLabel);
        gameSizePanel.add(columnField);
        gameSizePanel.add(rowLabel);
        gameSizePanel.add(rowField);
        gameSizePanel.add(resetButton);
        gameSizePanel.add(applySizeButton);

        // Bottom settings panel - player colors
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                colorButtons[i][j] = new JButton(colorMappingStrings[j]);
                colorButtons[i][j].setBackground(colorMappings[j]);
                colorButtons[i][j].setBorder(null);
                final int cmIndex = j;
                final int pcIndex = i;
                colorButtons[i][j].addActionListener(actionEvent -> {
                    playerColors[pcIndex] = colorMappings[cmIndex];
                });
                playerColorPanels[i].add(colorButtons[i][j]);
            }
        }

        // Add components to color panel container
        colorPanelContainer.add(playerColorPanels[0]);
        colorPanelContainer.add(playerColorPanels[1]);

        // Add the containers to the settings panel
        settingsPanel.add(gameSizePanel);
        settingsPanel.add(colorPanelContainer);

        // Add settings panel to settings frame
        settingsFrame.add(settingsPanel);
        settingsFrame.setVisible(false);
    }

    public void gameLoop() {
        while (true) {
            switch (key) {
                case KeyEvent.VK_LEFT:
                    column = (column == 1) ? 1 : column - 1;
                    key = 0;
                    break;

                case KeyEvent.VK_RIGHT:
                    int gameWidth = game.getWidth();
                    column = (column == gameWidth) ? gameWidth : column + 1;
                    key = 0;
                    break;

                case KeyEvent.VK_DOWN:
                    if (!blockInput) {
                        if (game.dropPiece(column, currentPlayer)) {
                            if (game.checkWin(currentPlayer)) {
                                frameDisplayString = "Player " + currentPlayer + " wins!";
                                wins[currentPlayer - 1]++;
                                blockInput = true;
                            } else {
                                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                                frameDisplayString = "Player " + currentPlayer + "'s turn";
                            }
                        } else {
                            frameDisplayString = "Column full";
                        }
                    }
                    key = 0;
                    break;

                case KeyEvent.VK_SPACE:
                    game.resetGame();
                    blockInput = false;
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    frameDisplayString = "Player " + currentPlayer + "'s turn";
                    key = 0;
                    break;

                case KeyEvent.VK_S:
                    settingsFrame.setVisible(true);
                    key = 0;
                    break;

                default:
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            gameFrame.repaint();
        }
    }

    private class GPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            int frameWidth = gameFrame.getWidth();
            int frameHeight = gameFrame.getHeight();
            int gameWidth = game.getWidth();
            int gameHeight = game.getHeight();
            int borderFactorMin = 7;
            int borderTop = frameHeight / borderFactorMin;
            int cellWidth = (frameHeight - borderTop * 2) / gameHeight;

            if ((cellWidth * gameWidth + frameWidth / (borderFactorMin * 2)) > frameWidth) {
                cellWidth = (frameWidth - frameWidth / (borderFactorMin * 2)) / gameWidth;
                borderTop = (frameHeight - cellWidth * gameHeight) / 2;
            }

            int borderLeft = (frameWidth - cellWidth * gameWidth) / 2;
            int cellHeight = cellWidth * gameHeight;

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, frameWidth, frameHeight);

            g.setColor(Color.WHITE);

            for (int i = 0; i <= gameWidth; i++) {
                g.drawLine(borderLeft + i * cellWidth, borderTop, borderLeft + i * cellWidth, borderTop + cellWidth * gameHeight);
            }
            g.drawLine(borderLeft, borderTop + cellHeight, borderLeft + cellWidth * gameWidth, borderTop + cellHeight);

            for (int i = 0; i < gameWidth; i++) {
                for (int j = 0; j < gameHeight; j++) {
                    int fieldPoint = game.getFieldPoint(i, j);
                    if (fieldPoint > 0) {
                        g.setColor(playerColors[fieldPoint - 1]);
                        g.fillOval(borderLeft + i * cellWidth, borderTop + j * cellWidth, cellWidth, cellWidth);
                    }
                }
            }
            g.setColor(playerColors[currentPlayer - 1]);
            g.fillOval(borderLeft + (column - 1) * cellWidth, borderTop - cellWidth, cellWidth, cellWidth);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(frameDisplayString, frameWidth / 2 - g.getFontMetrics().stringWidth(frameDisplayString) / 2, borderTop / 2);

            String winsString = wins[0] + " : " + wins[1];
            g.drawString(winsString, frameWidth / 2 - g.getFontMetrics().stringWidth(winsString) / 2, borderTop + cellHeight + borderTop / 2);
        }
    }
}
