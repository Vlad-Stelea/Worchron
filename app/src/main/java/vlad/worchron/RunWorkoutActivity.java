package vlad.worchron;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vlad.backend.Exercises.Exercise;
import vlad.backend.Exercises.WorkoutExercise;
import vlad.backend.Workout;

/**
 * Activity responsible for running and displaying all exercises in a workout
 */
public class RunWorkoutActivity extends AppCompatActivity {
    private Workout mWorkout;
    private List<WorkoutExercise> mExercises;
    private WorkoutDisplayer mWorkoutDisplayer;
    private static final int WORKOUT_DISPLAYER_ID = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_workout);
        //Get stuff passed in by the past activity
        mWorkout = (Workout) getIntent().getSerializableExtra(getString(R.string.run_workout_workout_key));
        //Set  the title of the workout
        ((TextView) findViewById(R.id.activity_run_workout_name_view)).setText(mWorkout.getName());
        //Set up rendering options
        mExercises = mWorkout.getExercises();
        ViewGroup mainLayout = findViewById(R.id.activity_run_workout_layout);
        mWorkoutDisplayer = new WorkoutDisplayer(this, mExercises);
        mainLayout.addView(mWorkoutDisplayer);
        mainLayout.
                getViewTreeObserver().
                addOnGlobalLayoutListener(() ->{
            setWorkoutDisplayerDimensions(mainLayout, mWorkoutDisplayer);
            positionWorkoutDisplayer((ConstraintLayout) mainLayout);
        });
    }

    /**
     * Sets the workoutdisplayer to the right dimensions so it looks good
     * @param mainLayout The main layout of the activity
     * @param displayer {@link WorkoutDisplayer} the displayer that needs to get sized
     */
    private void setWorkoutDisplayerDimensions(ViewGroup mainLayout, WorkoutDisplayer displayer){
        final int margins = 100;
        int height = mainLayout.getHeight();
        Log.d("TEST","Original Height is" + height);
        //Loop through all the children and subtract from the total height to find how much space this displayer has
        for(int i = 0; i < mainLayout.getChildCount(); i++){
            height -= mainLayout.getChildAt(i).getHeight();
        }
        Log.d("TEST","Final Height is" + height);
        displayer.getLayoutParams().height = height;
        displayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Constrains the workoutdisplayer to display as the last object to be displayed
     * @param layout the layout to be edited
     */
    private void positionWorkoutDisplayer(ConstraintLayout layout){
        mWorkoutDisplayer.setId(WORKOUT_DISPLAYER_ID);
        final int TOP_MARGIN = 0;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        //Connect the top of the workoutdisplayer to bottom of workout name
        constraintSet.connect(mWorkoutDisplayer.getId(),
                ConstraintSet.TOP, R.id.activity_run_workout_name_view,
                ConstraintSet.BOTTOM,
                TOP_MARGIN);
        layout.setConstraintSet(constraintSet);
    }

    /**
     * Layout responsible for Displaying and transitioning through a workout
     */
    public static class WorkoutDisplayer extends LinearLayout implements RunExerciseFragment.RunExerciseFragmentCallback{
        //The maximum number of exercises to show at one time
        private int NUMBER_EXERCISES_DISPLAYED;
        private List<RunExerciseFragment> mExerciseFragments;

        public WorkoutDisplayer(Context context, List<WorkoutExercise> exercises) {
            super(context);
            mExerciseFragments = generateRunExerciseFragmentsList(exercises);
        }

        /**
         * TODO
         * Calculates how many items should be displayed based on the size of this layout
         * @return the maximum number of items to be displayed at one time
         */
        public void calculateNumberOfWorkoutsDisplayed(){
            NUMBER_EXERCISES_DISPLAYED = 3;
        }

        /**
         * TODO
         * What to do when a fragment is done with it's actions
         */
        @Override
        public void onExerciseDone() {

        }

        private List<RunExerciseFragment> generateRunExerciseFragmentsList(List<WorkoutExercise> exercises){
            ArrayList<RunExerciseFragment> toReturn = new ArrayList<>();
            //Loop through the exercises in the passed in list and convert them into the fragments
            for(WorkoutExercise exercise: exercises){
                toReturn.add(exercise.generateRunExerciseFragment(this));
            }
            return toReturn;
        }
    }
}
