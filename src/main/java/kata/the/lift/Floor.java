package kata.the.lift;

import java.util.HashMap;
import java.util.Map;

import static kata.the.lift.Direction.DOWN;
import static kata.the.lift.Direction.UP;

public class Floor {
    private static Map<Integer, Floor> floorMap = new HashMap<>();

    private boolean isButtonUpActive;
    private boolean isButtonDownActive;
    private Integer floorNumber;

    private Floor(Integer floorNumber) {
        this.floorNumber = floorNumber;
        this.isButtonUpActive = false;
        this.isButtonDownActive = false;
        floorMap.put(floorNumber, this);
    }

    public static Floor floor(Integer floorNumber) {
        if (floorMap.get(floorNumber) == null) return new Floor(floorNumber);
        return floorMap.get(floorNumber);
    }

    public Integer floorNumber() {
        return floorNumber;
    }

    public boolean isFloorButtonActive(Direction direction) {
        return floorMap.get(floorNumber).isButtonActive(direction);
    }

    protected void setButtonActive(Direction direction) {
        if (direction.equals(UP) && !isButtonUpActive) this.isButtonUpActive = true;
        if (direction.equals(DOWN) && !isButtonDownActive) this.isButtonDownActive = true;
    }

    protected void clearButton(Direction direction) {
        if (direction.equals(UP) && isButtonUpActive) this.isButtonUpActive = false;
        if (direction.equals(DOWN) && isButtonDownActive) this.isButtonDownActive = false;
    }

    private boolean isButtonActive(Direction direction) {
        if (direction.equals(UP) && isButtonUpActive) return true;
        if (direction.equals(DOWN) && isButtonDownActive) return true;
        return false;
    }
}
