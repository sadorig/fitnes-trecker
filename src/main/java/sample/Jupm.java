package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Jupm {
    private IntegerProperty Time;
    private IntegerProperty Calories;

    public Jupm(int time, int calories) {
        Time = new SimpleIntegerProperty(time);
        Calories = new SimpleIntegerProperty (calories);
    }

    public Jupm() {this(0,0);}

    public int getCalories() {
        return Calories.get();
    }

    public IntegerProperty caloriesProperty() {
        return Calories;
    }

    public void setCalories(int calories) {
        this.Calories.set(calories);
    }

    public int getTime() {
        return Time.get();
    }

    public IntegerProperty timeProperty() {
        return Time;
    }

    public void setTime(int time) {
        this.Time.set(time);
    }
}
