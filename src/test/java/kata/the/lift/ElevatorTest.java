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
    public void lift_gets_call_from_floor_with_direction() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(3), UP);

        // Then
        assertThat(elevator.isStandby(), is(false));
    }

    @Test
    public void lift_can_return_its_current_location() {
        // Given
        Floor currentFloor = floor(2);
        Elevator elevator = new Elevator(currentFloor);

        // Then
        assertThat(elevator.currentFloor(), is(currentFloor));
    }

    @Test
    public void lift_gets_floor_request() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(5));

        // Then
        assertThat(elevator.isStandby(), is(false));
    }

    @Test
    public void lift_does_not_respond_to_the_same_requests() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(2));

        // Then
        assertThat(elevator.isStandby(), is(true));
    }

    @Test
    public void lift_travels_to_the_requested_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));
        elevator.request(floor(5));

        // When
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(5)));
    }

    @Test
    public void lift_stands_by_if_no_pending_request() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.drive();

        // Then
        assertThat(elevator.currentFloor(), is(floor(2)));
        assertThat(elevator.isStandby(), is(true));
    }

    @Test
    public void current_floor_monitor_displays_current_level() {
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
    public void double_requests_does_not_affect_lift_monitor() {
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
    public void current_floor_monitor_display_no_arrows_if_standing_by() {
        // Given & When
        Elevator elevator = new Elevator(floor(2));

        // Then
        assertThat(elevator.driveDirection(), is(nullValue()));
    }

    @Test
    public void current_floor_monitor_display_direction_u_pif_travelling_to_higher_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(6));

        // Then
        assertThat(elevator.driveDirection(), is(UP));
    }

    @Test
    public void current_floor_monitor_display_direction_d_o_w_nif_travelling_to_lower_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(-2));

        // Then
        assertThat(elevator.driveDirection(), is(DOWN));
    }

    @Test
    public void current_floor_monitor_display_direction_u_pif_called_from_higher_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(6), DOWN);

        // Then
        assertThat(elevator.driveDirection(), is(UP));
    }

    @Test
    public void current_floor_monitor_display_direction_d_o_w_nif_called_from_lower_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(-2), UP);

        // Then
        assertThat(elevator.driveDirection(), is(DOWN));
    }

    @Test
    public void lift_calls_takes_priority_over_floor_calls() {
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
        assertThat(floor(3).isFloorButtonActive(UP), is(false));
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
        assertThat(floor(12).isFloorButtonActive(DOWN), is(false));
    }

    @Test
    public void asses_if_the_buttons_are_being_highlighted_properly() {
        // Given
        Elevator elevator = new Elevator(floor(23));
        elevator.request(floor(6));

        // When
        boolean wasButtonDownActiveBeforeFloorCall = floor(12).isFloorButtonActive(DOWN);
        elevator.floorCall(floor(12), DOWN);
        boolean wasButtonDownActiveBeforeElevatorPass = floor(12).isFloorButtonActive(DOWN);
        boolean wasButtonUpActiveBeforeElevatorPass = floor(12).isFloorButtonActive(UP);
        elevator.drive();

        // Then
        assertThat(wasButtonDownActiveBeforeFloorCall, is(false));
        assertThat(wasButtonDownActiveBeforeElevatorPass, is(true));
        assertThat(wasButtonUpActiveBeforeElevatorPass, is(false));
        assertThat(floor(12).isFloorButtonActive(DOWN), is(false));
    }
}