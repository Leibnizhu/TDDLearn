package io.gitlab.leibnizhu.tddtest.ch04ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class PointSpec {

    private Point point;
    private final int x = 12;
    private final int y = 21;

    @BeforeMethod
    public void beforeTest() {
        point = new Point(x, y);
    }

    public void whenInstantiatedThenXIsSet() {
        assertEquals(point.getX(), x);
    }

    public void whenInstantiatedThenYIsSet() {
        assertEquals(point.getY(), y);
    }

    public void equalTest(){
        assertFalse(point.equals(""));
        assertFalse(point.equals(new Point(11,21)));
        assertFalse(point.equals(new Point(12,22)));
        assertTrue(point.equals(new Point(12,21)));
    }
}
