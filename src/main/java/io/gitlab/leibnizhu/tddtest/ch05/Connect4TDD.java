package io.gitlab.leibnizhu.tddtest.ch05;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
public class Connect4TDD {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int WIN_DISCS = 4;
    private static final String EMPTY = " ";
    private static final String RED = "R";
    private static final String GREEN = "G";
    private static final String DELIMITER = "|";

    private String[][] board = new String[ROWS][COLUMNS];
    private String currentPlayer = RED;
    private String winner = "";
    private PrintStream out;

    public Connect4TDD(PrintStream out) {
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        this.out = out;
    }

    public int getNumberOfDiscs() {
        return IntStream.range(0, COLUMNS).map(this::getDiscNumberOfColumn).sum();
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
        checkWinner(row, column);
        printBoard();
        return row;
    }

    private void checkWinner(int row, int column) {
        if(winner.isEmpty()){
            String curColor = board[row][column];
            Pattern win = Pattern.compile(".*" + curColor + "{" + WIN_DISCS +"}.*");
            //垂直和水平判定
            String vertical = IntStream.range(0, ROWS).mapToObj(r -> board[r][column]).reduce(String::concat).get();
            String horizontal = Stream.of(board[row]).reduce(String::concat).get();
            if(win.matcher(vertical).matches() || win.matcher(horizontal).matches()){
                winner = curColor;
                return;
            }
            //对角线判定1
            int startOff = Math.min(row, column);
            int myColumn = column - startOff, myRow = row - startOff;
            StringJoiner joiner = new StringJoiner("");
            do{
                joiner.add(board[myRow++][myColumn++]);
            }while (myColumn < COLUMNS && myRow < ROWS);
            if(win.matcher(joiner.toString()).matches()){
                winner = curColor;
                return;
            }
            //对角线判定2
            startOff = Math.min(ROWS - 1 - row, column);
            myColumn = column - startOff;
            myRow = row + startOff;
            joiner = new StringJoiner("");
            do{
                joiner.add(board[myRow--][myColumn++]);
            }while (myColumn < COLUMNS && myRow >= 0);
            if(win.matcher(joiner.toString()).matches()){
                winner = curColor;
            }
        }
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
        if (column < 0 || column >= COLUMNS) {
            throw new RuntimeException("Invalid column " + column);
        }
    }

    public String getCurrentPlayer() {
        out.printf("Player %s turn", currentPlayer);
        return currentPlayer;
    }

    public boolean isFinished() {
        return getNumberOfDiscs() == ROWS * COLUMNS;
    }

    public String getWinner() {
        return winner;
    }
}
