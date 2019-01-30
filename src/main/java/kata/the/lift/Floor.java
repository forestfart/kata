package kata.the.lift;

import java.util.HashMap;
import java.util.Map;

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

    public boolean isButtonUpActive() {
        return floorMap.get(floorNumber).isButtonUpActive;
    }

    public boolean isButtonDownActive() {
        return floorMap.get(floorNumber).isButtonDownActive;
    }

    protected void setButtonUpActive() {
        this.isButtonUpActive = true;
    }

    protected void setButtonDownActive() {
        this.isButtonDownActive = true;
    }

    public void clearButtonUp() {
        this.isButtonUpActive = false;
    }

    public void clearButtonDown() {
        this.isButtonDownActive = false;
    }
}
