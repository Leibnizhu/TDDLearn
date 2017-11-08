package io.gitlab.leibnizhu.tddtest.ch04ship;

public class Ship {
    private final Planet planet;
    private final Location location;

    public Ship(Location location, Planet planet) {
        this.location = location;
        this.planet = planet;
    }

    public Location getLocation() {
        return location;
    }

    public Planet getPlanet() {
        return planet;
    }

    public boolean moveForward() {
        return location.forward(planet.getMax());
    }

    public boolean moveBackward() {
        return location.backward(planet.getMax());
    }

    public Ship turnLeft() {
        location.turnLeft();
        return this;
    }

    public Ship turnRight() {
        location.turnRight();
        return this;
    }

    public void receiveCommand(String command) {
        switch (command) {
            case "F":
                moveForward();
                break;
            case "B":
                moveBackward();
                break;
            case "L":
                turnLeft();
                break;
            case "R":
                turnRight();
                break;
            default:
        }
    }

    public void receiveCommands(String commands) {
        for(String command : commands.split("")){
            receiveCommand(command);
        }
    }
}
