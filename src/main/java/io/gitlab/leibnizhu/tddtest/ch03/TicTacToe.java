package io.gitlab.leibnizhu.tddtest.ch03;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-07 13:10.
 */
public class TicTacToe {

    private static final int BOARD_MIN = 1;
    private static final int BOARD_MAX = 3;

    private static final int X_PUT = 1;
    private static final int O_PUT = 2;
    private static final char[] PLAYER_NAME = {'\0', 'X', 'O'};

    private int lastPlayer = 0;

    private int[][] board = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayerIndex();
        setBox(x, y);
        if (isWin(x, y)) {
            return PLAYER_NAME[lastPlayer] + " is winner";
        } else if (isDraw()) {
            return "Draw";
        } else {
            return "No Winner";
        }
    }

    private boolean isDraw() {
        for (int i = 0; i < BOARD_MAX; i++) {
            for (int j = 0; j < BOARD_MAX; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWin(int x, int y) {
        int playerTotal = lastPlayer * BOARD_MAX;
        int verticle = 0;
        int horizontal = 0;
        int diagonal1 = 0;
        int diagonal2 = 0;
        for (int i = 0; i < BOARD_MAX; i++) {
            verticle += board[i][y - 1];
            horizontal += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][BOARD_MAX - i - 1];
        }
        if (verticle == playerTotal || horizontal == playerTotal || diagonal1 == playerTotal || diagonal2 == playerTotal) {
            return true;
        }
        return false;
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != 0) {
            throw new RuntimeException("Box is occupied!");
        } else {
            board[x - 1][y - 1] = lastPlayer;
        }
    }

    private void checkAxis(int axis) {
        if (axis < BOARD_MIN || axis > BOARD_MAX) {
            throw new RuntimeException("X/Y is outside board!");
        }
    }

    public char nextPlayer() {
        return PLAYER_NAME[nextPlayerIndex()];
    }

    private int nextPlayerIndex() {
        if (lastPlayer == X_PUT) {
            return O_PUT;
        }
        return X_PUT;
    }
}
