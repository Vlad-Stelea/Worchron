package vlad.backend.Exercises;

import android.app.Activity;
import android.content.Context;

import java.io.Serializable;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.EditWorkout;
import vlad.worchron.RenameDialog;
import vlad.worchron.RunExerciseFragment;

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

    /**
     * Converts the workout exercise that this is called from into the corresponding {@link RunExerciseFragment}
     * @param callback {@link vlad.worchron.RunExerciseFragment.RunExerciseFragmentCallback} Class that implements the correct callback so the fragment can interact with it
     * @return The correct {@link RunExerciseFragment} that represents this exercise and should be run
     */
    public abstract RunExerciseFragment generateRunExerciseFragment(RunExerciseFragment.RunExerciseFragmentCallback callback);

}
