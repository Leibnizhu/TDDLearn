package io.gitlab.leibnizhu.tddtest.ch05;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
public class Connect4TDD {
    private static final int ROWS = 6;
    private static final int COLUMN = 7;
    private static final String EMPTY = " ";

    private String[][] board = new String[ROWS][COLUMN];

    public Connect4TDD(){
        for(String[] row : board){
            Arrays.fill(row, EMPTY);
        }
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
        board[row][column] = "X";
        return row;
    }

    private void checkPositionTOInsert(int row, int column) {
        if(row >= ROWS){
            throw new RuntimeException("No more room in column " + column);
        }
    }

    private void checkColumn(int column) {
        if(column < 0 || column >= COLUMN){
            throw new RuntimeException("Invalid column " + column);
        }
    }
}
