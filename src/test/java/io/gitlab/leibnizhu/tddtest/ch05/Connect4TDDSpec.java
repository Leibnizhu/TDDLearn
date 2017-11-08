package io.gitlab.leibnizhu.tddtest.ch05;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.testng.Assert.*;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-08 21:49.
 */
@Test
public class Connect4TDDSpec {
    private Connect4TDD connect4;
    private OutputStream os;

    @BeforeMethod
    public void beforeEachTest() {
        os = new ByteArrayOutputStream();
        this.connect4 = new Connect4TDD(new PrintStream(os));
    }

    /**
     * #1 初始化
     */
    public void whenGameStartedTheBoardIsEmpty() {
        assertEquals(connect4.getNumberOfDiscs(), 0);
    }

    /**
     * #2 越界判定1
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Invalid column -?\\d")
    public void whenDiscOutsideThenRuntimeException() {
        int column = -1;
        connect4.putDiscInColumn(column);
    }

    /**
     * #2 越界判定2
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Invalid column -?\\d")
    public void whenDiscOutside2ThenRuntimeException() {
        int column = 8;
        connect4.putDiscInColumn(column);
    }

    /**
     * #2 插入返回值1
     */
    public void whenFirstDiscInsertThenPositionIsZero() {
        int column = 1;
        assertEquals(connect4.putDiscInColumn(column), 0);
    }

    /**
     * #2 插入返回值2
     */
    public void whenSecondDiscInsertedThenPositionIsOne() {
        int column = 2;
        connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), 1);
    }

    /**
     * #2 插入返回值3
     */
    public void whenDiscInsertedThenNumberOfDiscIncrease() {
        int column = 3;
        connect4.putDiscInColumn(column);
        int position = connect4.putDiscInColumn(column);
        assertEquals(connect4.putDiscInColumn(column), position + 1);
    }

    /**
     * #2 列满判定
     */
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "No more room in column \\d")
    public void whenNoMoreRoomInColumnThenRunTImeException() {
        int column = 4;
        int maxDiscInColumn = 6;
        for (int i = 0; i < maxDiscInColumn; i++) {
            connect4.putDiscInColumn(column);
        }
        connect4.putDiscInColumn(column);
    }

    /**
     * #3 红子先走
     */
    public void whenFirstPlayThenDIscColorIsRed() {
        assertEquals(connect4.getCurrentPlayer(), "R");
    }

    /**
     * #3 然后绿子走
     */
    public void whenSecondPlayThenDIscColorIsGreen() {
        int column = 1;
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "G");
    }

    /**
     * #3 红绿交替走
     */
    public void whenGreenPlayThenNextIsRed() {
        int column = 1;
        assertEquals(connect4.getCurrentPlayer(), "R");
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "G");
        connect4.putDiscInColumn(column);
        assertEquals(connect4.getCurrentPlayer(), "R");
    }

    /**
     * #4 输出当前玩家
     */
    public void whenAskedForCurrentPlayerThenOutputNotice() {
        connect4.getCurrentPlayer();
        assertTrue(os.toString().contains("Player R turn"));
    }

    /**
     * #4 下棋后输出棋盘
     */
    public void whenDiscInsertThenPrintBoard() {
        int column = 1;
        connect4.putDiscInColumn(column);
        assertTrue(os.toString().contains("| |R| | | | | |"));
    }

    /**
     * #5 开始游戏时肯定未结束
     */
    public void whenGameStartThenNotFinished() {
        assertFalse(connect4.isFinished(), "The game must not be finished");
    }

    /**
     * #5 全部填满时结束
     */
    public void whenNoDiscCanBeInsertedThenGameIsFinished() {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                connect4.putDiscInColumn(column);
            }
        }
        assertTrue(connect4.isFinished(), "The game must be finished");
    }

    /**
     * #6 垂直连续4个获胜
     */
    public void when4VerticleConnectedThenWin() {
        for (int row = 0; row < 3; row++) {
            connect4.putDiscInColumn(1);//R
            connect4.putDiscInColumn(2);//G
        }
        assertEquals(connect4.getWinner(), "");
        connect4.putDiscInColumn(1);//R
        assertEquals(connect4.getWinner(), "R");
    }

    /**
     * #7 水平连续4个获胜
     */
    public void when4HorizontalConnectedThenWin() {
        int column;
        for (column = 0; column < 3; column++) {
            connect4.putDiscInColumn(column);//R
            connect4.putDiscInColumn(column);//G
        }
        assertEquals(connect4.getWinner(), "");
        connect4.putDiscInColumn(column);//R
        assertEquals(connect4.getWinner(), "R");
    }

    /**
     * #8 对角线4个连续获胜1
     */
    public void when4Diagonal1ConnectedThenWin() {
        int[] play = new int[]{1, 2, 2, 3, 4, 3, 3, 4, 4, 5, 4};
        for (int column : play) {
            connect4.putDiscInColumn(column);
        }
        assertEquals(connect4.getWinner(), "R");
    }

    /**
     * #8 对角线4个连续获胜2
     */
    public void when4Diagonal2ConnectedThenWin() {
        int[] play = new int[]{3, 4, 2, 3, 2, 2, 1, 1, 1, 1};
        for (int column : play) {
            connect4.putDiscInColumn(column);
        }
        assertEquals(connect4.getWinner(), "G");
    }
}