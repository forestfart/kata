package kata.the.lift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Lift {
    private Map<Floor, Direction> calls = new HashMap<>();
    private Floor currentFloor;
    private boolean isStandby = true;
    private LinkedList<Floor> floorRequests = new LinkedList<>();

    public Lift(Floor floor) {
        this.currentFloor = floor;
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        calls.put(floorLevel, direction);
        isStandby = false;
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public void requestFloor(Floor requestedFloor) {
        if (requestedFloor != currentFloor) {
            floorRequests.add(requestedFloor);
            isStandby = false;
        }
    }

    public boolean isStandby() {
        return isStandby;
    }

    public void drive() {
        if(floorRequests.size() != 0 && currentFloor != floorRequests.getFirst()) {
            currentFloor = floorRequests.getFirst();
        }
    }
}
