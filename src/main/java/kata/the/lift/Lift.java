package kata.the.lift;

import java.util.HashMap;
import java.util.Map;


public class Lift {
    private Map<Floor, Direction> calls = new HashMap<>();
    private Floor currentFlor;
    public boolean isStandingBy;

    public Lift(Floor floor) {
        this.currentFlor = floor;
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        calls.put(floorLevel, direction);
        isStandingBy = false;
    }

    public Floor currentFloor() {
        return currentFlor;
    }
}
