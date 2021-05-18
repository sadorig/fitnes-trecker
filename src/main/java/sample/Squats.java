package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Squats {
    private IntegerProperty Time;
    private IntegerProperty Calories;

    public Squats(int time, int calories) {
        Time = new SimpleIntegerProperty (time);
        Calories = new SimpleIntegerProperty (calories);
    }

    public Squats() {this(0,0);}

    public int getTime() {
        return Time.get();
    }

    public IntegerProperty timeProperty() {
        return Time;
    }

    public void setTime(int time) {
        this.Time.set(time);
    }

    public int getCalories() {
        return Calories.get();
    }

    public IntegerProperty caloriesProperty() {
        return Calories;
    }

    public void setCalories(int calories) {
        this.Calories.set(calories);
    }
}
