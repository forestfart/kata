package kata.the.lift;

public class FloorCall implements FloorNumber {
    private final Floor floorLevel;
    private final Direction direction;

    public FloorCall(Floor floorLevel, Direction direction) {

        this.floorLevel = floorLevel;
        this.direction = direction;
    }

    public Integer floorNumber() {
        return floorLevel.floorNumber();
    }

    public Direction direction() {
        return direction;
    }
}
