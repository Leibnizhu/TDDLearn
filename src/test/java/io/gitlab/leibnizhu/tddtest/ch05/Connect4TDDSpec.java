package io.gitlab.leibnizhu.tddtest.ch05;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
@Test
public class Connect4TDDSpec {
    private Connect4TDD connect4;

    @BeforeMethod
    public void beforeEachTest(){
        this.connect4 = new Connect4TDD();
    }

    public void whenGameStartedTheBoardIsEmpty(){
        assertEquals(connect4.getNumberOfDiscs(), 0);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Invalid column -?\\d")
    public void whenDiscOutsideThenRuntimeException(){
        int column = -1;
        connect4.putDiscInColumn(column);
    }

    public void whenFirstDiscInsertThenPositionIsZero(){
        int column = 1;
        assertEquals(connect4.putDiscInColumn(column), 0);
    }

    public void whenSecondDiscInsertedThenPositionIsOne(){
        int column = 2;
        connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), 1);
    }

    public void whenDiscInsertedThenNumberOfDiscIncrease(){
        int column = 3;
        connect4.putDiscInColumn(column);
        int position = connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), position + 1);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "No more room in column \\d")
    public void whenNoMoreRoomInColumnThenRunTImeException(){
        int column = 4;
        int maxDiscInColumn = 6;
        for(int i = 0; i< maxDiscInColumn;i++){
            connect4.putDiscInColumn(column);
        }
        connect4.putDiscInColumn(column);
    }
}