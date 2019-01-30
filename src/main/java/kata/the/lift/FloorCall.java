package kata.the.lift;

public class FloorCall {
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
