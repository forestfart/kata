package kata.the.lift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Lift {
    private Map<Floor, Direction> calls = new HashMap<>();
    private Floor currentFlor;
    private boolean isStandingBy = true;
    private List<Floor> floorRequests = new LinkedList<>();

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

    public void requestFloor(Floor floor) {
        floorRequests.add(floor);
        isStandingBy = false;
    }

    public boolean status() {
        return isStandingBy;
    }
}
