package kata.the.elevator;

import java.util.LinkedList;

import static java.lang.String.format;
import static kata.the.elevator.Direction.DOWN;
import static kata.the.elevator.Direction.UP;
import static kata.the.elevator.Floor.floor;


public class Elevator {
    private LinkedList<Floor> requestsOnTheWayUp = new LinkedList<>();
    private LinkedList<Floor> requestsOnTheWayDown = new LinkedList<>();
    private LinkedList<Floor> requestsUpQueue = new LinkedList<>();
    private LinkedList<Floor> requestsDownQueue = new LinkedList<>();
    private Direction travelDirection = null;
    private Floor currentFloor;

    public Elevator(Floor floor) {
        this.currentFloor = floor;
        monitorDisplay("Newly initialised elevator on level %d");
    }

    public void floorCall(Floor floor, Direction direction) {
        floor.setButtonActive(direction);
        if (direction.equals(UP)) requestsUpQueue.add(floor);
        if (direction.equals(DOWN)) requestsDownQueue.add(floor);
        pushQueue();
        travelDirection = resolveNewCycle();
    }

    private void pushQueue() {
        for (Floor callUp : requestsUpQueue) {
            if (currentFloor.floorNumber() < callUp.floorNumber()) {
                requestsOnTheWayUp.add(callUp);
                requestsUpQueue.remove(callUp);
            }
        }
        for (Floor callDown : requestsDownQueue) {
            if (currentFloor.floorNumber() > callDown.floorNumber()) {
                requestsOnTheWayDown.add(callDown);
                requestsDownQueue.remove(callDown);
            }
        }
    }

    private Direction resolveNewCycle() {

        if (isCalledToRun()) {
            if (!requestsOnTheWayUp.isEmpty()) return UP;
            return DOWN;
        }
        return null;
    }

    public void request(Floor requestedFloor) {
        if (requestedFloor.floorNumber() > currentFloor.floorNumber()) {
            requestsOnTheWayUp.add(requestedFloor);
        }
        if (requestedFloor.floorNumber() < currentFloor.floorNumber()) {
            requestsOnTheWayDown.add(requestedFloor);
        }
        travelDirection = resolveNewCycle();
    }

    public void run() throws RuntimeException {
        travelDirection = resolveNewCycle();
        while (isCalledToRun()) {
            moveToNextFloor();

            if (requiredToStop()) {
                monitorDisplay("Stopping at level: %d");
                currentFloor.clearButton(travelDirection);
                markOffCurrentFloor();
            }
        }
    }

    private boolean requiredToStop() throws RuntimeException {
        if (travelDirection.equals(UP)) {
            return requestsOnTheWayUp.stream().anyMatch(n -> n.floorNumber().equals(currentFloor.floorNumber()));
        }
        if (travelDirection.equals(DOWN)) {
            return requestsOnTheWayDown.stream().anyMatch(n -> n.floorNumber().equals(currentFloor.floorNumber()));
        }
        throw new RuntimeException("something went terribly wrong!");
    }

    private void moveToNextFloor() {
        if (travelDirection.equals(UP)) currentFloor = floor(currentFloor.floorNumber() + 1);
        if (travelDirection.equals(DOWN)) currentFloor = floor(currentFloor.floorNumber() - 1);
        monitorDisplay("Moving to level: %d");
    }

    private void markOffCurrentFloor() {
        if (travelDirection.equals(UP)) {
            requestsOnTheWayUp.removeIf(n -> n.floorNumber().equals(currentFloor.floorNumber()));
        }
        if (travelDirection.equals(DOWN)) {
            requestsOnTheWayDown.removeIf(n -> n.floorNumber().equals(currentFloor.floorNumber()));
        }
    }

    private boolean isCalledToRun() {
        if (requestsOnTheWayUp.isEmpty() && requestsOnTheWayDown.isEmpty()) {
            if (!requestsDownQueue.isEmpty()) {
                resolveInitialTravelDirection(requestsDownQueue.poll());
                return true;
            }
            if (!requestsUpQueue.isEmpty()) {
                resolveInitialTravelDirection(requestsUpQueue.poll());
                return true;
            }
            travelDirection = null;
            return false;
        }
        return true;
    }


    private void resolveInitialTravelDirection(Floor destinationFloor) {
        if (currentFloor.floorNumber() < destinationFloor.floorNumber()) {
            requestsOnTheWayUp.add(destinationFloor);
            travelDirection = UP;
        }
        if (currentFloor.floorNumber() > destinationFloor.floorNumber()) {
            requestsOnTheWayDown.add(destinationFloor);
            travelDirection = DOWN;
        }
    }

    private void monitorDisplay(String message) {
        System.out.println(format(message, currentFloor.floorNumber()));
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public Direction travelDirection() {
        return travelDirection;
    }

    public boolean isStandingBy() {
        return travelDirection == null;
    }
}
