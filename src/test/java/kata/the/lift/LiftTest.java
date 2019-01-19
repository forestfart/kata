package kata.the.lift;

import org.junit.Test;

import static kata.the.lift.Direction.UP;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LiftTest {

    @Test
    public void liftGetsCallFromFloorWithDirection() {
        // Given
        Lift lift = new Lift(new Floor(2));

        // When
        lift.floorCall(new Floor(3), UP);

        // Then
        assertThat(lift.isStandingBy, is(false));
    }

    @Test
    public void liftCanReturnItsCurrentLocation() {
        // Given
        Floor currentFloor = new Floor(2);
        Lift lift = new Lift(currentFloor);

        // Then
        assertThat(lift.currentFloor(), is(currentFloor));

    }
}