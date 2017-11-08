package io.gitlab.leibnizhu.tddtest.ch05;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.testng.Assert.assertEquals;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
@Test
public class Connect4TDDSpec {
    private Connect4TDD connect4;
    private OutputStream os;

    @BeforeMethod
    public void beforeEachTest(){
        os = new ByteArrayOutputStream();
        this.connect4 = new Connect4TDD(new PrintStream(os));
    }

    /**
     * 初始化
     */
    public void whenGameStartedTheBoardIsEmpty(){
        assertEquals(connect4.getNumberOfDiscs(), 0);
    }

    /**
     * 越界判定1
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Invalid column -?\\d")
    public void whenDiscOutsideThenRuntimeException(){
        int column = -1;
        connect4.putDiscInColumn(column);
    }

    /**
     * 越界判定2
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Invalid column -?\\d")
    public void whenDiscOutside2ThenRuntimeException(){
        int column = 8;
        connect4.putDiscInColumn(column);
    }

    /**
     * 插入返回值1
     */
    public void whenFirstDiscInsertThenPositionIsZero(){
        int column = 1;
        assertEquals(connect4.putDiscInColumn(column), 0);
    }

    /**
     * 插入返回值2
     */
    public void whenSecondDiscInsertedThenPositionIsOne(){
        int column = 2;
        connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), 1);
    }

    /**
     * 插入返回值3
     */
    public void whenDiscInsertedThenNumberOfDiscIncrease(){
        int column = 3;
        connect4.putDiscInColumn(column);
        int position = connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), position + 1);
    }

    /**
     * 列满判定
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "No more room in column \\d")
    public void whenNoMoreRoomInColumnThenRunTImeException(){
        int column = 4;
        int maxDiscInColumn = 6;
        for(int i = 0; i< maxDiscInColumn;i++){
            connect4.putDiscInColumn(column);
        }
        connect4.putDiscInColumn(column);
    }

    /**
     * 红子先走
     */
    public void whenFirstPlayThenDIscColorIsRed(){
        assertEquals(connect4.getCurrentPlayer(), "R");
    }

    /**
     * 然后绿子走
     */
    public void whenSecondPlayThenDIscColorIsGreen(){
        int column = 1;
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "G");
    }

    /**
     * 红绿交替走
     */
    public void whenGreenPlayThenNextIsRed(){
        int column = 1;
        assertEquals(connect4.getCurrentPlayer(), "R");
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "G");
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "R");
    }
}