package io.gitlab.leibnizhu.tddtest.ch03;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Leibniz.Hu
 * Created on 2017-11-07 13:09.
 */
public class TicTacToeSpec {
    private TicTacToe ticTacToe;

    @BeforeMethod
    public final void before() {
        ticTacToe = new TicTacToe();
    }

    /**
     * X边界条件
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void whenXOutsideBoardThenRuntimeException() {
        ticTacToe.play(5, 2);

    }

    @Test(expectedExceptions = RuntimeException.class)
    public void whenXNegativeThenRuntimeException() {
        ticTacToe.play(-2, 2);

    }

    /**
     * Y边界条件
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void whenYOutsideBoardThenRuntimeException() {
        ticTacToe.play(2, 5);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void whenYNegativeThenRuntimeException() {
        ticTacToe.play(-1, 5);
    }

    /**
     * 重复下子
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(1, 2);
        ticTacToe.play(1, 2);
    }

    /**
     * 下一个选手1
     */
    @Test
    public void givenFirstTUrnWhenNextPlayerThenX() {
        assertEquals('X', ticTacToe.nextPlayer());
    }

    /**
     * 下一个选手2
     */
    @Test
    public void givenLastTurnWasXWhenPlayerThenO() {
        ticTacToe.play(1, 1);
        assertEquals('O', ticTacToe.nextPlayer());
    }

    /**
     * 获胜规则1
     */
    @Test
    public void whenPlayThenNoWinner() {
        assertEquals("No Winner", ticTacToe.play(1, 1));
    }

    /**
     * 获胜规则2 水平制胜
     */
    @Test
    public void whenPlayAndWholeHorizontalLineThenWInner() {
        ticTacToe.play(1, 1);//X
        ticTacToe.play(1, 2);//O
        ticTacToe.play(2, 1);//X
        ticTacToe.play(2, 2);//O
        String result = ticTacToe.play(3, 1);//X
        assertEquals("X is winner", result);
    }

    /**
     * 获胜规则3 垂直制胜
     */
    @Test
    public void whenPlayAndWholeVerticleLineThenWInner() {
        ticTacToe.play(2, 1);//X
        ticTacToe.play(1, 1);//O
        ticTacToe.play(3, 1);//X
        ticTacToe.play(1, 2);//O
        ticTacToe.play(2, 2);//X
        String result = ticTacToe.play(1, 3);//O
        assertEquals("O is winner", result);
    }

    /**
     * 获胜规则4 左对角线左制胜
     */
    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWInner() {
        ticTacToe.play(1, 1);//X
        ticTacToe.play(1, 2);//O
        ticTacToe.play(2, 2);//X
        ticTacToe.play(1, 3);//O
        String result = ticTacToe.play(3, 3);//X
        assertEquals("X is winner", result);
    }

    /**
     * 获胜规则4 右对角线左制胜
     */
    @Test
    public void whenPlayAndBottomDiagonalLineThenWInner() {
        ticTacToe.play(1, 1);//X
        ticTacToe.play(1, 3);//O
        ticTacToe.play(1, 2);//X
        ticTacToe.play(2, 2);//O
        ticTacToe.play(3, 2);//X
        String result = ticTacToe.play(3, 1);//O
        assertEquals("O is winner", result);
    }

    /**
     * 平局规则 全部沾满
     */
    @Test
    public void whenAllBoxAreFilledThenDraw(){
        ticTacToe.play(1, 1);//X
        ticTacToe.play(1, 2);//O
        ticTacToe.play(1, 3);//X
        ticTacToe.play(2, 1);//O
        ticTacToe.play(2, 3);//X
        ticTacToe.play(2, 2);//O
        ticTacToe.play(3, 1);//X
        ticTacToe.play(3, 3);//O
        String result = ticTacToe.play(3, 2);//X
        assertEquals("Draw", result);
    }
}
