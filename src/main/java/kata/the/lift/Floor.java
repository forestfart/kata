package kata.the.lift;

import java.util.HashMap;
import java.util.Map;

public class Floor {
    private static Map<Integer, Floor> floorMap = new HashMap<>();

    private Integer floorNumber;

    private Floor(Integer floorNumber) {
        this.floorNumber = floorNumber;
        floorMap.put(floorNumber, this);
    }

    public static Floor floor(Integer floorNumber) {
        if (floorMap.get(floorNumber) == null) return new Floor(floorNumber);
        return floorMap.get(floorNumber);
    }

    public Integer getNumber() {
        return floorNumber;
    }
}
