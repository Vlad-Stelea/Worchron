package vlad.backend.Exercises;

import android.content.Context;

import java.io.Serializable;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.RunExerciseView;

/**
 * Class to represent an exercise once it's been set up for a workout
 */
public abstract class WorkoutExercise implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * Converts the workout exercise that this is called from into the corresponding {@link RunExerciseView}
     * @param context the context from where this is called
     * @param callback {@link vlad.worchron.RunExerciseView.RunExerciseFragmentCallback} Class that implements the correct callback so the fragment can interact with it
     * @return The correct {@link RunExerciseView} that represents this exercise and should be run
     */
    public abstract RunExerciseView generateRunExerciseFragment(Context context, RunExerciseView.RunExerciseFragmentCallback callback);

}
