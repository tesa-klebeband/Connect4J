package de.tesa_klebeband.connect4j;

public class Connect4 {
    private int columns = 7;
    private int height = 6;
    private int field[][] = new int[columns][height];

    public void resetGame() {
        field = new int[columns][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < columns; j++) {
                field[j][i] = 0;
            }
        }
    }

    public boolean dropPiece(int column, int player) {
        column--;
        boolean dropSuccess = false;
        for (int i = height - 1; i >= 0; i--) {
            if (field[column][i] == 0) {
                field[column][i] = player;
                dropSuccess = true;
                break;
            }
        }
        return dropSuccess;
    }

    public boolean checkWin(int player) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < columns; j++) {
                if (field[j][i] == player) {
                    // Check horizontal
                    if (j < columns - 3) {
                        if (field[j + 1][i] == player && field[j + 2][i] == player && field[j + 3][i] == player) {
                            return true;
                        }
                    }
                    // Check vertical
                    if (i < height - 3) {
                        if (field[j][i + 1] == player && field[j][i + 2] == player && field[j][i + 3] == player) {
                            return true;
                        }
                    }
                    // Check diagonal
                    if (j < columns - 3 && i < height - 3) {
                        if (field[j + 1][i + 1] == player && field[j + 2][i + 2] == player && field[j + 3][i + 3] == player) {
                            return true;
                        }
                    }
                    if (j < columns - 3 && i >= 3) {
                        if (field[j + 1][i - 1] == player && field[j + 2][i - 2] == player && field[j + 3][i - 3] == player) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getWidth() {
        return columns;
    }

    public int getHeight() {
        return height;
    }

    public void setColumns(int width) {
        this.columns = width;

    }

    public void setRows(int height) {
        this.height = height;
    }

    public int getFieldPoint(int x, int y) {
        return field[x][y];
    }
}
