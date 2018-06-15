package vlad.backend.Exercises;

import android.app.Activity;

import java.io.Serializable;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.EditWorkout;
import vlad.worchron.RenameDialog;

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

    /**
     * Creates an edit exercise dialog so that the user can edit the exercise
     * @return An edit exercise dialog for the specific object
     */
    public abstract EditExerciseDialog getEditExerciseDialog();

}
