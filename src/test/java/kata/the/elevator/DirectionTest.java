package kata.the.elevator;

import org.junit.Test;

import static kata.the.elevator.Direction.*;
import static kata.the.elevator.Direction.UP;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DirectionTest {
    @Test
    public void test_revert_up() {
        // When
        Direction revertedDirection = revertDirection(UP);

        // Then
        assertThat(revertedDirection, is(DOWN));
    }

    @Test
    public void test_revert_down() {
        // When
        Direction revertedDirection = revertDirection(DOWN);

        // Then
        assertThat(revertedDirection, is(UP));
    }

}