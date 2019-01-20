package kata.the.lift;

import org.junit.Test;

import static kata.the.lift.Direction.UP;
import static kata.the.lift.Floor.floor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class LiftTest {

    @Test
    public void liftGetsCallFromFloorWithDirection() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        lift.floorCall(floor(3), UP);

        // Then
        assertThat(lift.isStandby(), is(false));
    }

    @Test
    public void liftCanReturnItsCurrentLocation() {
        // Given
        Floor currentFloor = floor(2);
        Lift lift = new Lift(currentFloor);

        // Then
        assertThat(lift.currentFloor(), is(currentFloor));
    }

    @Test
    public void liftGetsFloorRequest() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        lift.request(floor(5));

        // Then
        assertThat(lift.isStandby(), is(false));
    }

    @Test
    public void liftDoesNotRespondToTheSameRequests() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        lift.request(floor(2));

        // Then
        assertThat(lift.isStandby(), is(true));
    }

    @Test
    public void liftTravelsToTheRequestedLevel() {
        // Given
        Lift lift = new Lift(floor(2));
        lift.request(floor(5));

        // When
        lift.drive();

        // Then
        assertThat(lift.currentFloor(), is(floor(5)));
    }

    @Test
    public void liftStandsByIfNoPendingRequest() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        lift.drive();

        // Then
        assertThat(lift.currentFloor(), is(floor(2)));
        assertThat(lift.isStandby(), is(true));
    }

    @Test
    public void currentFloorMonitorDisplaysCurrentLevel() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        Floor currentLevelDisplayStep0 = lift.currentFloor();
        lift.request(floor(3));
        lift.drive();
        Floor currentLevelDisplayStep1 = lift.currentFloor();
        lift.request(floor(4));
        lift.drive();
        Floor currentLevelDisplayStep2 = lift.currentFloor();
        lift.request(floor(0));
        lift.drive();
        Floor currentLevelDisplayStep3 = lift.currentFloor();

        // Then
        assertThat(currentLevelDisplayStep0, is(floor(2)));
        assertThat(currentLevelDisplayStep1, is(floor(3)));
        assertThat(currentLevelDisplayStep2, is(floor(4)));
        assertThat(currentLevelDisplayStep3, is(floor(0)));
    }

    @Test
    public void doubleRequestsDoesNotAffectLiftMonitor() {
        // Given
        Lift lift = new Lift(floor(2));

        // When
        Floor currentLevelDisplayStep0 = lift.currentFloor();
        lift.request(floor(3));
        lift.drive();
        lift.request(floor(3));
        lift.drive();
        Floor currentLevelDisplayStep1 = lift.currentFloor();
        lift.request(floor(4));
        lift.drive();
        Floor currentLevelDisplayStep2 = lift.currentFloor();

        // Then
        assertThat(currentLevelDisplayStep0, is(floor(2)));
        assertThat(currentLevelDisplayStep1, is(floor(3)));
        assertThat(currentLevelDisplayStep2, is(floor(4)));
    }

    @Test
    public void currentFloorMonitorDisplayNoArrowsIfStandingBy() {
        // Given & When
        Lift lift = new Lift(floor(2));

        // Then
        assertThat(lift.driveDirection(), is(nullValue()));

    }
}