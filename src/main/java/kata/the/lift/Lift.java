package kata.the.lift;

import java.util.HashMap;
import java.util.Map;


public class Lift {
    private Map<Floor, Direction> calls = new HashMap<>();
    public boolean isStandingBy;

    public void floorCall(Floor floorLevel, Direction direction) {
        calls.put(floorLevel, direction);
        isStandingBy = false;
    }
}
