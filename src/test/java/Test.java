import sample.ControllerJump;
import sample.ControllerPushUp;
import sample.ControllerSquats;

import static org.junit.jupiter.api.Assertions.*;

public class Test {
    @org.junit.jupiter.api.Test
    public void testPushUp() {
        ControllerPushUp pushUp = new ControllerPushUp();
        pushUp.timer +=2;
        assertEquals(pushUp.getStatistics(),50);
    }

    @org.junit.jupiter.api.Test
    public void testJump() {
        ControllerJump jump = new ControllerJump();
        jump.timer +=2;
        assertEquals(jump.getStatistics(),30);
    }

    @org.junit.jupiter.api.Test
    public void testSquat() {
        ControllerSquats squats = new ControllerSquats();
        squats.timer +=2;
        assertEquals(squats.getStatistics(),40);
    }

}
