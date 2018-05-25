package backend;

import java.io.Serializable;

/**
 * Class to represent an exercise once it's been set up for a workout
 */
public abstract class WorkoutExercise implements Serializable {
    //The name of this exercise
    protected String mName;
    public WorkoutExercise(String name){
        mName = name;
    }

    public abstract String toString();
}
