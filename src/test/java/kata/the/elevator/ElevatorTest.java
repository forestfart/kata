package kata.the.elevator;

import org.junit.Test;

import static kata.the.elevator.Direction.DOWN;
import static kata.the.elevator.Direction.UP;
import static kata.the.elevator.Floor.floor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ElevatorTest {

    @Test
    public void elevator_gets_call_from_floor_with_direction() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(3), UP);

        // Then
        assertThat(elevator.isStandingBy(), is(false));
    }

    @Test
    public void elevator_can_return_its_current_location() {
        // Given
        Floor currentFloor = floor(2);
        Elevator elevator = new Elevator(currentFloor);

        // Then
        assertThat(elevator.currentFloor(), is(currentFloor));
    }

    @Test
    public void elevator_gets_floor_request() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(5));

        // Then
        assertThat(elevator.isStandingBy(), is(false));
    }

    @Test
    public void elevator_does_not_respond_to_the_same_requests() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(2));

        // Then
        assertThat(elevator.isStandingBy(), is(true));
    }

    @Test
    public void elevator_travels_to_the_requested_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));
        elevator.request(floor(5));

        // When
        elevator.run();

        // Then
        assertThat(elevator.currentFloor(), is(floor(5)));
    }

    @Test
    public void elevator_stands_by_if_no_pending_request() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.run();

        // Then
        assertThat(elevator.currentFloor(), is(floor(2)));
        assertThat(elevator.isStandingBy(), is(true));
    }

    @Test
    public void current_floor_monitor_displays_current_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        Floor currentLevelDisplayStep0 = elevator.currentFloor();
        elevator.request(floor(3));
        elevator.run();
        Floor currentLevelDisplayStep1 = elevator.currentFloor();
        elevator.request(floor(4));
        elevator.run();
        Floor currentLevelDisplayStep2 = elevator.currentFloor();
        elevator.request(floor(0));
        elevator.run();
        Floor currentLevelDisplayStep3 = elevator.currentFloor();

        // Then
        assertThat(currentLevelDisplayStep0, is(floor(2)));
        assertThat(currentLevelDisplayStep1, is(floor(3)));
        assertThat(currentLevelDisplayStep2, is(floor(4)));
        assertThat(currentLevelDisplayStep3, is(floor(0)));
    }

    @Test
    public void double_requests_does_not_affect_elevator_monitor() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        Floor currentLevelDisplayStep0 = elevator.currentFloor();
        elevator.request(floor(3));
        elevator.run();
        elevator.request(floor(3));
        elevator.run();
        Floor currentLevelDisplayStep1 = elevator.currentFloor();
        elevator.request(floor(4));
        elevator.run();
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
        assertThat(elevator.travelDirection(), is(nullValue()));
    }

    @Test
    public void current_floor_monitor_display_direction_up_if_travelling_to_higher_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(6));

        // Then
        assertThat(elevator.travelDirection(), is(UP));
    }

    @Test
    public void current_floor_monitor_display_direction_down_if_travelling_to_lower_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(-2));

        // Then
        assertThat(elevator.travelDirection(), is(DOWN));
    }

    @Test
    public void current_floor_monitor_display_direction_up_if_called_from_higher_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(6), DOWN);

        // Then
        assertThat(elevator.travelDirection(), is(UP));
    }

    @Test
    public void current_floor_monitor_display_direction_d_o_w_nif_called_from_lower_level() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.floorCall(floor(-2), UP);

        // Then
        assertThat(elevator.travelDirection(), is(DOWN));
    }

    @Test
    public void elevator_calls_takes_priority_over_floor_calls() {
        // Given
        Elevator elevator = new Elevator(floor(2));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(-2), UP);

        // Then
        assertThat(elevator.travelDirection(), is(UP));
    }

    @Test
    public void elevator_will_stop_at_called_floor_if_it_is_n_its_way_up() {
        // Given
        Elevator elevator = new Elevator(floor(-2));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(3), UP);
        elevator.run();

        // Then
        assertThat(elevator.currentFloor(), is(floor(6)));
        assertThat(floor(3).isFloorButtonActive(UP), is(false));
    }

    @Test
    public void elevator_will_stop_at_called_floor_if_it_is_on_its_way_down() {
        // Given
        Elevator elevator = new Elevator(floor(23));

        // When
        elevator.request(floor(6));
        elevator.floorCall(floor(12), DOWN);
        elevator.run();

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
        elevator.run();

        // Then
        assertThat(wasButtonDownActiveBeforeFloorCall, is(false));
        assertThat(wasButtonDownActiveBeforeElevatorPass, is(true));
        assertThat(wasButtonUpActiveBeforeElevatorPass, is(false));
        assertThat(floor(12).isFloorButtonActive(DOWN), is(false));
    }

    @Test
    public void elevator_will_move_up_and_down_then_standby_until_next_move_down_and_up() {
        // Given
        Elevator elevator = new Elevator(floor(0));

        // When
        elevator.floorCall(floor(56), DOWN);
        elevator.run();
        elevator.request(floor(0));
        boolean isStandby1 = elevator.isStandingBy();
        Floor standbyFloor1 = elevator.currentFloor();
        elevator.floorCall(floor(-5), UP);
        elevator.run();
        elevator.request(floor(26));
        elevator.run();
        boolean isStandby2 = elevator.isStandingBy();
        Floor standbyFloor2 = elevator.currentFloor();

        // Then
        assertThat(isStandby1, is(false));
        assertThat(standbyFloor1, is(floor(56)));
        assertThat(isStandby2, is(true));
        assertThat(standbyFloor2, is(floor(26)));
    }

    //add test when callfrom floor where elevator is at the moment
}