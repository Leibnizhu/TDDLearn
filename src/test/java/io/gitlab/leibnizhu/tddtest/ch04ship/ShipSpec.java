package io.gitlab.leibnizhu.tddtest.ch04ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class ShipSpec {
    private Planet planet;
    private Location location;
    private Ship ship;

    @BeforeMethod //等价于JUint的@Before
    public void beforeTest() {
        Point max = new Point(50, 50);
        this.location = new Location(new Point(21, 13), Direction.NORTH);
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(44, 44));
        obstacles.add(new Point(45, 46));
        this.planet = new Planet(max, obstacles);
        this.ship = new Ship(this.location, this.planet);
    }

    public void whenInstantiatedTHenLocationIsSet() {
        assertEquals(ship.getLocation(), location);
    }

    public void whenInstantiatedTHenPlanetIsSet() {
        assertEquals(ship.getPlanet(), planet);
    }

    /**
     * 前进
     */
    public void whenMoveForwardThenForward() {
        Location expected = location.copy();
        expected.forward();
        ship.moveForward();
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 后退
     */
    public void whenMoveBackwardThenBackward() {
        Location expected = location.copy();
        expected.backward();
        ship.moveBackward();
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 左转
     */
    public void whenTurnLeftThenTurnLeft() {
        Location expected = location.copy();
        expected.turnLeft();
        ship.turnLeft();
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 右转
     */
    public void whenTurnRightThenTurnRight() {
        Location expected = location.copy();
        expected.turnRight();
        ship.turnRight();
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 接受F命令
     */
    public void whenReceiveCommandFThenForward() {
        Location expected = location.copy();
        expected.forward();
        ship.receiveCommand("F");
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 接受B命令
     */
    public void whenReceiveCommandBThenBackward() {
        Location expected = location.copy();
        expected.backward();
        ship.receiveCommand("B");
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 接受L命令
     */
    public void whenReceiveCommandLThenTurnLeft() {
        Location expected = location.copy();
        expected.turnLeft();
        ship.receiveCommand("L");
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 接受R命令
     */
    public void whenReceiveCommandRThenTurnRight() {
        Location expected = location.copy();
        expected.turnRight();
        ship.receiveCommand("R");
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 执行组合命令
     */
    public void whenReceiveCommands() {
        Location expected = location.copy();
        expected.forward();
        expected.backward();
        expected.turnLeft();
        ship.receiveCommands("FBL");
        assertEquals(ship.getLocation(), expected);

        expected.turnLeft();
        expected.forward();
        ship.receiveCommands("LF");
        assertEquals(ship.getLocation(), expected);
    }

    /**
     * 前进超越东边边界
     */
    public void whenForwardOverpassEastBoundaryThenGoWest(){
        location.setDirection(Direction.EAST);
        location.getPoint().setX(planet.getMax().getX());
        ship.receiveCommand("F");
        assertEquals(location.getX(), 1);
    }

    /**
     * 倒退超越西边边界
     */
    public void whenBackwardOverpassWestBoundaryThenGoWest(){
        location.setDirection(Direction.EAST);
        location.getPoint().setX(1);
        ship.receiveCommand("B");
        assertEquals(location.getX(), planet.getMax().getX());
    }

    /**
     * 遇到陆地障碍
     */
    public void whenReceiveCommandsThenStopOnObstacle() {
        //设置障碍
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(location.getX() + 1, location.getY()));
        ship.getPlanet().setObstacles(obstacles);
        //期望地点
        Location expected = location.copy();
        expected.turnRight();
        expected.forward(planet.getMax(), obstacles);
        expected.turnLeft();
        expected.backward(new Point(0, 0), obstacles);
        //发出指令
        ship.receiveCommands("RFLB");
        assertEquals(ship.getLocation(), expected);
    }

    public void whenReceiveCommandsThenOForOkAndXForObstacle() {
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(location.getX() + 1, location.getY()));
        ship.getPlanet().setObstacles(obstacles);
        String status = ship.receiveCommands("RFLB");
        assertEquals(status, "OXOO");
    }
}
