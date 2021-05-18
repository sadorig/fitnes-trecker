package sample;


import javafx.beans.property.IntegerProperty;

public class Exercises {
    private final IntegerProperty pushUp;
    private  final IntegerProperty jump;
    private  final IntegerProperty squats;

    public Exercises(IntegerProperty pushUp, IntegerProperty jump, IntegerProperty squats) {
        this.pushUp = pushUp;
        this.jump = jump;
        this.squats = squats;
    }

    public int getPushUp() {
        return pushUp.get();
    }

    public IntegerProperty pushUpProperty() {
        return pushUp;
    }

    public void setPushUp(int pushUp) {
        this.pushUp.set(pushUp);
    }

    public int getJump() {
        return jump.get();
    }

    public IntegerProperty jumpProperty() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump.set(jump);
    }

    public int getSquats() {
        return squats.get();
    }

    public IntegerProperty squatsProperty() {
        return squats;
    }

    public void setSquats(int squats) {
        this.squats.set(squats);
    }
}
