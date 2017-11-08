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

    public String moveForward() {
        return location.forward(planet.getMax(), planet.getObstacles()) ? "O" : "X";
    }

    public String moveBackward() {
        return location.backward(planet.getMax(), planet.getObstacles()) ? "O" : "X";
    }

    public String turnLeft() {
        location.turnLeft();
        return "O";
    }

    public String turnRight() {
        location.turnRight();
        return "O";
    }

    public String receiveCommand(String command) {
        switch (command) {
            case "F":
                return moveForward();
            case "B":
                return moveBackward();
            case "L":
                return turnLeft();
            case "R":
                return turnRight();
            default:
        }
        return "X";
    }

    public String receiveCommands(String commands) {
        StringBuilder sbuf = new StringBuilder();
        for(String command : commands.split("")){
            sbuf.append(receiveCommand(command));
        }
        return sbuf.toString();
    }
}
