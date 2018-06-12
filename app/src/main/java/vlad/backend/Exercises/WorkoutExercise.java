package vlad.backend.Exercises;

import java.io.Serializable;

/**
 * Class to represent an exercise once it's been set up for a workout
 */
public abstract class WorkoutExercise implements Serializable {
    //The name of this exercise
    protected SelectableExercise mExercise;

    public WorkoutExercise(SelectableExercise exercise){
        mExercise = exercise;
    }

    public String getName(){
        return mExercise.getName();
    }

}
