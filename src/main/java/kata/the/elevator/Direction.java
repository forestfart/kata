package kata.the.elevator;

public enum Direction {
    UP, DOWN;

    protected static Direction revertDirection(Direction direction) {
        if(direction.equals(UP)) return DOWN;
        return UP;
    }

}
