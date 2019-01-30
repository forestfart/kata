package kata.the.lift;

import org.junit.Test;

import static kata.the.lift.Direction.DOWN;
import static kata.the.lift.Direction.UP;
import static kata.the.lift.Floor.floor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ElevatorTest {

    @Test
    public void liftGetsCallFromFloorWithDirection() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(3), UP);

        // Then
        assertThat(elevator.isStandby(), is(false));
    }

    @Test
    public void liftCanReturnItsCurrentLocation() {
        // Given
        Floor currentFloor = floor(2);
        Elevator elevator = new Elevator(currentFloor);

        // Then
        assertThat(elevator.currentFloor(), is(currentFloor));
    }

    @Test
    public void liftGetsFloorRequest() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(5));

        // Then
        assertThat(elevator.isStandby(), is(false));
    }

    @Test
    public void liftDoesNotRespondToTheSameRequests() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(2));

        // Then
        assertThat(elevator.isStandby(), is(true));
    }

    @Test
    public void liftTravelsToTheRequestedLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));
        elevator.request(floor(5));

        // When
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(5)));
    }

    @Test
    public void liftStandsByIfNoPendingRequest() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(2)));
        assertThat(elevator.isStandby(), is(true));
    }

    @Test
    public void currentFloorMonitorDisplaysCurrentLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        Floor currentLevelDisplayStep0 = elevator.currentFloor();
        elevator.request(floor(3));
        elevator.drive();
        Floor currentLevelDisplayStep1 = elevator.currentFloor();
        elevator.request(floor(4));
        elevator.drive();
        Floor currentLevelDisplayStep2 = elevator.currentFloor();
        elevator.request(floor(0));
        elevator.drive();
        Floor currentLevelDisplayStep3 = elevator.currentFloor();

        // Then
        assertThat(currentLevelDisplayStep0, is(floor(2)));
        assertThat(currentLevelDisplayStep1, is(floor(3)));
        assertThat(currentLevelDisplayStep2, is(floor(4)));
        assertThat(currentLevelDisplayStep3, is(floor(0)));
    }

    @Test
    public void doubleRequestsDoesNotAffectLiftMonitor() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        Floor currentLevelDisplayStep0 = elevator.currentFloor();
        elevator.request(floor(3));
        elevator.drive();
        elevator.request(floor(3));
        elevator.drive();
        Floor currentLevelDisplayStep1 = elevator.currentFloor();
        elevator.request(floor(4));
        elevator.drive();
        Floor currentLevelDisplayStep2 = elevator.currentFloor();

        // Then
        assertThat(currentLevelDisplayStep0.floorNumber(), equalTo(2));
        assertThat(currentLevelDisplayStep1.floorNumber(), equalTo(3));
        assertThat(currentLevelDisplayStep2.floorNumber(), equalTo(4));
    }

    @Test
    public void currentFloorMonitorDisplayNoArrowsIfStandingBy() {
        // Given & When
        Elevator elevator = new Elevator(floor(2));

        // Then
        assertThat(elevator.driveDirection(), is(nullValue()));
    }

    @Test
    public void currentFloorMonitorDisplayDirectionUPifTravellingToHigherLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(6));

        // Then
        assertThat(elevator.driveDirection(), is(UP));
    }

    @Test
    public void currentFloorMonitorDisplayDirectionDOWNifTravellingToLowerLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(-2));

        // Then
        assertThat(elevator.driveDirection(), is(DOWN));
    }

    @Test
    public void currentFloorMonitorDisplayDirectionUPifCalledFromHigherLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(6), DOWN);

        // Then
        assertThat(elevator.driveDirection(), is(UP));
    }


    @Test
    public void currentFloorMonitorDisplayDirectionDOWNifCalledFromLowerLevel() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(-2), UP);

        // Then
        assertThat(elevator.driveDirection(), is(DOWN));
    }

    @Test
    public void liftCallsTakesPriorityOverFloorCalls() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(-2), UP);

        // Then
        assertThat(elevator.driveDirection(), is(UP));
    }

    @Test
    public void lift_will_stop_at_called_floor_if_it_is_n_its_way_up() {
        // Given
        Elevator elevator = new Elevator(floor(-2));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(3), UP);
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(6)));
        assertThat(floor(3).isButtonUpActive(), is(false));
    }

    @Test
    public void lift_will_stop_at_called_floor_if_it_is_on_its_way_down() {
        // Given
        Elevator elevator = new Elevator(floor(23));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(12), DOWN);
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(6)));
        assertThat(floor(122).isButtonDownActive(), is(false));
    }

}