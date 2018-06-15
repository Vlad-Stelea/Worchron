package vlad.backend.Exercises;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.EditWorkout;
import vlad.worchron.RenameDialog;

/**
 * Class that represents an exercise that is done in reps eg. curls, pushups
 */
public class RepExercise extends WorkoutExercise {
    //The number of sets for this exercise
    private int mSets;
    //How many reps for each set
    private int mReps;

    /**
     * Constructs a RepExercises object
     * @param repExercise the exercise that this will be a version of
     * @param sets how many sets of this exercise will occur
     * @param reps how many reps of this exercise will occur
     */
    public RepExercise(SelectableRepExercise repExercise, int sets, int reps){
        super(repExercise);
        mSets = sets;
        mReps = reps;
    }

    public int getSets(){
        return mSets;
    }

    public int getReps(){
        return mReps;
    }

    /**
     * Creates an edit exercise dialog so that the user can edit the exercise
     * @return
     */
    @Override
    public EditExerciseDialog getEditExerciseDialog() {
        return null;
    }


    public static class ExerciseStep implements RenameDialog.Renamable{
        private String mName;

        public ExerciseStep(String name){
            mName = name;
        }

        public String getName(){
            return mName;
        }

        /**
         * Sets a new name for the object
         *
         * @param newName the objects new name
         */
        @Override
        public void rename(String newName) {
            mName = newName;
        }
    }
}
