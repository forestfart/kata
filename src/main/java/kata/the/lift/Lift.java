package kata.the.lift;

import java.util.LinkedList;

import static java.lang.String.format;
import static kata.the.lift.Direction.DOWN;
import static kata.the.lift.Direction.UP;


public class Lift {
    private LinkedList<FloorCall> callsFromFloors = new LinkedList<>();
    private LinkedList<Floor> liftRequests = new LinkedList<>();
    private Floor currentFloor;
    private Direction nextFloorDirection = null;

    public Lift(Floor floor) {
        this.currentFloor = floor;
        monitorDisplay("Newly initialised lift on level %d");
    }

    private Direction resolveTravelDirectionAfterNewRequest() {
        if (liftRequests.getFirst().floorNumber() > currentFloor.floorNumber()) return nextFloorDirection = UP;
        return nextFloorDirection = DOWN;
    }

    private Direction resolveTravelDirectionAfterNewFloorCall() {
        if (callsFromFloors.getFirst().floorLevel() > currentFloor.floorNumber()) return nextFloorDirection = UP;
        return nextFloorDirection = DOWN;
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        callsFromFloors.add(new FloorCall(floorLevel, direction));
        nextFloorDirection = resolveTravelDirectionAfterNewFloorCall();
    }

    public void request(Floor requestedFloor) {
        if (requestedFloor != currentFloor) {
            liftRequests.add(requestedFloor);
            nextFloorDirection = resolveTravelDirectionAfterNewRequest();
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
