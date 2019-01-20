package kata.the.lift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.String.format;


public class Lift {
    private Map<Floor, Direction> callsFromFloors = new HashMap<>();
    private Floor currentFloor;
    private LinkedList<Floor> floorRequests = new LinkedList<>();

    public Lift(Floor floor) {
        this.currentFloor = floor;
        monitorDisplay("Newly initialised lift on level %d");
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        callsFromFloors.put(floorLevel, direction);
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public void requestFloor(Floor requestedFloor) {
        if (requestedFloor != currentFloor) {
            floorRequests.add(requestedFloor);
        }
    }

    public void drive() {
        if(floorRequests.size() != 0 && currentFloor != floorRequests.getFirst()) {
            currentFloor = floorRequests.getFirst();
            floorRequests.removeFirst();
            monitorDisplay("Lift arrives on level %d");
        }
    }

    public boolean isStandby() {
        if(floorRequests.size() == 0 && callsFromFloors.size() == 0) return true;
        return false;
    }

    private void monitorDisplay(String message) {
        System.out.println(format(message, currentFloor.floorNumber()));
    }
}
