package io.gitlab.leibnizhu.tddtest.ch05;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
public class Connect4TDD {
    private static final int ROWS = 6;
    private static final int COLUMN = 7;
    private static final String EMPTY = " ";
    private static final String RED = "R";
    private static final String GREEN = "G";
    private static final String DELIMITER = "|";

    private String[][] board = new String[ROWS][COLUMN];
    private String currentPlayer = RED;
    private PrintStream out;

    public Connect4TDD(PrintStream out) {
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        this.out = out;
    }

    public int getNumberOfDiscs() {
        return IntStream.range(0, COLUMN).map(this::getDiscNumberOfColumn).sum();
    }

    private int getDiscNumberOfColumn(int column) {
        return (int) IntStream.range(0, ROWS).filter(r -> !EMPTY.equals(board[r][column])).count();
    }

    public int putDiscInColumn(int column) {
        checkColumn(column);
        int row = getDiscNumberOfColumn(column);
        checkPositionTOInsert(row, column);
        board[row][column] = currentPlayer;
        switchPlayer();
        printBoard();
        return row;
    }

    private void printBoard() {
        for (int row = ROWS - 1; row >= 0; row--) {
            StringJoiner joiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
            Stream.of(board[row]).forEachOrdered(joiner::add);
            out.println(joiner.toString());
        }
    }

    private void switchPlayer() {
        currentPlayer = RED.equals(currentPlayer) ? GREEN : RED;
    }

    private void checkPositionTOInsert(int row, int column) {
        if (row >= ROWS) {
            throw new RuntimeException("No more room in column " + column);
        }
    }

    private void checkColumn(int column) {
        if (column < 0 || column >= COLUMN) {
            throw new RuntimeException("Invalid column " + column);
        }
    }

    public String getCurrentPlayer() {
        out.printf("Player %s turn", currentPlayer);
        return currentPlayer;
    }
}
