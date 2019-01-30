package kata.the.lift;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;

import static java.lang.String.format;
import static kata.the.lift.Direction.DOWN;
import static kata.the.lift.Direction.UP;
import static kata.the.lift.Floor.floor;


public class Elevator {
    private LinkedList<FloorCall> callsFromFloors = new LinkedList<>();
    private LinkedList<Floor> elevatorRequests = new LinkedList<>();
    private Direction travelDirection = null;
    private Floor currentFloor;

    public Elevator(Floor floor) {
        this.currentFloor = floor;
        monitorDisplay("Newly initialised lift on level %d");
    }

    private Direction resolveTravelDirectionAfterNewRequest() {
        if (elevatorRequests.getFirst().floorNumber() > currentFloor.floorNumber()) return travelDirection = UP;
        return travelDirection = DOWN;
    }

    private Direction resolveTravelDirectionAfterNewFloorCall() {
        if (callsFromFloors.getFirst().floorNumber() > currentFloor.floorNumber()) return travelDirection = UP;
        return travelDirection = DOWN;
    }

    public void floorCall(Floor floorLevel, Direction direction) {
        floorLevel.setButtonActive(direction);
        callsFromFloors.add(new FloorCall(floorLevel, direction));
        if (elevatorRequests.size() == 0) travelDirection = resolveTravelDirectionAfterNewFloorCall();
    }

    public void request(Floor requestedFloor) {
        if (requestedFloor.floorNumber() != currentFloor.floorNumber()) {
            elevatorRequests.add(requestedFloor);
            travelDirection = resolveTravelDirectionAfterNewRequest();
        }
    }

    public void drive() {
        if(travelDirection != null && checkIfNeedsToContinueDirection()) {
            if (travelDirection.equals(UP)) currentFloor = floor(currentFloor.floorNumber() + 1);
            if (travelDirection.equals(DOWN)) currentFloor = floor(currentFloor.floorNumber() - 1);
            monitorDisplay("Moving to level: %d");
            if (needToStopAtThisLevel()) {
                monitorDisplay("Stopping at level: %d");
                currentFloor.clearButton(travelDirection);
            }
            tickOffCurrentFloor();
        }
    }

    private boolean needToStopAtThisLevel() {
        if(elevatorRequests.contains(currentFloor)) return true;
        return callsFromFloors.stream().anyMatch(n -> n.floorNumber().equals(currentFloor.floorNumber()) && n.direction().equals(travelDirection));
    }

    private void tickOffCurrentFloor() {
        elevatorRequests.removeIf(n -> n.floorNumber().equals(currentFloor.floorNumber()));
        callsFromFloors.removeIf(n -> n.floorNumber().equals(currentFloor.floorNumber()) && n.direction().equals(travelDirection));
        if(!elevatorRequests.isEmpty() || !callsFromFloors.isEmpty() || checkIfNeedsToContinueDirection()) drive();
    }

    private boolean checkIfNeedsToContinueDirection() {
        Optional<Floor> topRequest = elevatorRequests.stream().max(Comparator.comparing(Floor::floorNumber));
        Optional<Floor> bottomRequest = elevatorRequests.stream().min(Comparator.comparing(Floor::floorNumber));
        Optional<FloorCall> topCall = callsFromFloors.stream().max(Comparator.comparing(FloorCall::floorNumber));
        Optional<FloorCall> bottomCall = callsFromFloors.stream().min(Comparator.comparing(FloorCall::floorNumber));
        if(topRequest.isPresent()) {
            if (topRequest.get().floorNumber() > currentFloor.floorNumber()) return true;
            travelDirection = DOWN;
            if(!isStandby()) return true;
        }
        if(topCall.isPresent()) {
            if (topCall.get().floorNumber() > currentFloor.floorNumber()) return true;
            travelDirection = DOWN;
            if(!isStandby()) return true;
        }
        if(bottomRequest.isPresent()) {
            if (bottomRequest.get().floorNumber() < currentFloor.floorNumber()) return true;
            travelDirection = UP;
            if(!isStandby()) return true;
        }
        if(bottomCall.isPresent()) {
            if (bottomCall.get().floorNumber() > currentFloor.floorNumber()) return true;
            travelDirection = UP;
            if(!isStandby()) return true;
        }
        return false;
     }

    public boolean isStandby() {
        if(elevatorRequests.isEmpty() && callsFromFloors.isEmpty()) {
            travelDirection = null;
            return true;
        }
        return false;
    }

    private void monitorDisplay(String message) {
        System.out.println(format(message, currentFloor.floorNumber()));
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public Direction driveDirection() {
        return travelDirection;
    }
}
