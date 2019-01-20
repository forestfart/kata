package kata.the.lift;

public class FloorCall {
    private final Floor floorLevel;
    private final Direction direction;

    public FloorCall(Floor floorLevel, Direction direction) {

        this.floorLevel = floorLevel;
        this.direction = direction;
    }

    public Integer floorLevel() {
        return floorLevel.floorNumber();
    }

}
