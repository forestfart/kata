package kata.the.lift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Lift {
    private Map<Floor, Direction> calls = new HashMap<>();
    private Floor currentFlor;
    private boolean isStandby = true;
    private LinkedList floorRequests = new LinkedList<>();

    public Lift(Floor floor) {
        this.currentFlor = floor;
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        calls.put(floorLevel, direction);
        isStandby = false;
    }

    public Floor currentFloor() {
        return currentFlor;
    }

    public void requestFloor(Floor requestedFloor) {
        if (requestedFloor != currentFlor) {
            floorRequests.add(requestedFloor);
            isStandby = false;
        }
    }

    public boolean isStandby() {
        return isStandby;
    }

}
