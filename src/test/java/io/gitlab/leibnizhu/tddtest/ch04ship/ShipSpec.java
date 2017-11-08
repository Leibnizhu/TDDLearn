package io.gitlab.leibnizhu.tddtest.ch04ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class ShipSpec {
    private Planet planet;
    private Location location;
    private Ship ship;

    @BeforeMethod //等价于JUint的@Before
    public void beforeTest() {
        this.location = new Location(new Point(21, 13), Direction.NORTH);
        this.planet = new Planet(new Point(50, 50));
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
     * 倒退超越东边边界
     */
    public void whenBackwardOverpassEastBoundaryThenGoWest(){
        location.setDirection(Direction.EAST);
        location.getPoint().setX(planet.getMax().getX());
        ship.turnRight().turnRight();
        ship.receiveCommand("B");
        assertEquals(location.getX(), 1);
    }
}
