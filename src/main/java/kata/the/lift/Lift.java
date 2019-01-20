package kata.the.lift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.String.format;


public class Lift {
    private Map<Floor, Direction> callsFromFloors = new HashMap<>();
    private LinkedList<Floor> liftRequests = new LinkedList<>();
    private Floor currentFloor;
    private Direction nextFloorDirection = null;

    public Lift(Floor floor) {
        this.currentFloor = floor;
        monitorDisplay("Newly initialised lift on level %d");
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        callsFromFloors.put(floorLevel, direction);
    }

    public void request(Floor requestedFloor) {
        if (requestedFloor != currentFloor) {
            liftRequests.add(requestedFloor);
        }
    }

    public void drive() {
        if(liftRequests.size() != 0 && currentFloor != liftRequests.getFirst()) {
            currentFloor = liftRequests.getFirst();
            liftRequests.removeFirst();
            monitorDisplay("Lift arrives on level %d");
        }
    }

    public boolean isStandby() {
        if(liftRequests.size() == 0 && callsFromFloors.size() == 0) return true;
        return false;
    }

    private void monitorDisplay(String message) {
        System.out.println(format(message, currentFloor.floorNumber()));
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public Direction driveDirection() {
        return nextFloorDirection;
    }
}
