package vlad.worchron;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlad.backend.Exercises.WorkoutExercise;
import vlad.backend.Workout;

/**
 * Activity responsible for running and displaying all exercises in a workout
 */
public class RunWorkoutActivity extends AppCompatActivity implements InitializeLayoutListener.FinishInitializeCallback{
    private Workout mWorkout;
    private List<WorkoutExercise> mExercises;
    private WorkoutDisplayer mWorkoutDisplayer;
    private ViewGroup mainLayout;
    private FloatingActionButton mLeftControlButton, mCenterControlButton, mRightControlButton;
    private CurrentExerciseState mExerciseState = CurrentExerciseState.PAUSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_workout);
        //Get stuff passed in by the past activity
        mWorkout = (Workout) getIntent().getSerializableExtra(getString(R.string.run_workout_workout_key));
        //Set  the title of the workout
        ((TextView) findViewById(R.id.activity_run_workout_name_view)).setText(mWorkout.getName());
        //Set up player control buttons
        mLeftControlButton = findViewById(R.id.activity_run_workout_left_button);
        mLeftControlButton.setOnClickListener(this::onLeftButtonClicked);
        mRightControlButton = findViewById(R.id.activity_run_workout_right_button);
        mRightControlButton.setOnClickListener(this::onRightButtonClicked);
        mCenterControlButton = findViewById(R.id.activity_run_workout_center_button);
        mCenterControlButton.setOnClickListener(this::onCenterButtonClicked);

        //Set up rendering options
        mExercises = mWorkout.getExercises();
        mainLayout = findViewById(R.id.activity_run_workout_layout);
        mWorkoutDisplayer = new WorkoutDisplayer(this, mExercises);
        mainLayout.addView(mWorkoutDisplayer);
        InitializeLayoutListener listener = new InitializeLayoutListener(mainLayout, mWorkoutDisplayer, this);
        mainLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(listener);
    }

    //<-----------------------ActionListenerEvents------------------------------->
    //Buttons

    private void onLeftButtonClicked(View view){
        if(mExerciseState == CurrentExerciseState.PAUSED){
            mWorkoutDisplayer.startPreviousExercise();
            mWorkoutDisplayer.pauseExercise();
            mCenterControlButton.setImageDrawable(getDrawable(R.drawable.ic_play));
        }else if(mExerciseState == CurrentExerciseState.RUNNING){
            mWorkoutDisplayer.restartExercise();
            mExerciseState = CurrentExerciseState.PAUSED;
            mCenterControlButton.setImageDrawable(getDrawable(R.drawable.ic_play));
        }

    }


    private void onCenterButtonClicked(View view){
        if(mExerciseState == CurrentExerciseState.PAUSED){
            mCenterControlButton.setImageDrawable(getDrawable(R.drawable.ic_pause));
            mExerciseState = CurrentExerciseState.RUNNING;
            mWorkoutDisplayer.startExercise();
        }else if(mExerciseState == CurrentExerciseState.RUNNING){
            mCenterControlButton.setImageDrawable(getDrawable(R.drawable.ic_play));
            mExerciseState = CurrentExerciseState.PAUSED;
            mWorkoutDisplayer.pauseExercise();
        }
    }


    private void onRightButtonClicked(View view){
        mWorkoutDisplayer.startNextExercise();
    }

    //<-----------------------Overriding methods--------------------------------->
    @Override
    public void onFinishDrawing(InitializeLayoutListener listener) {
        mainLayout.getViewTreeObserver()
                .removeOnGlobalLayoutListener(listener);
    }

    /**
     * Layout responsible for Displaying and transitioning through a workout
     */
    public static class WorkoutDisplayer extends LinearLayout implements RunExerciseView.RunExerciseFragmentCallback{
        //The maximum number of exercises to show at one time
        private int MAX_NUMBER_EXERCISES_DISPLAYED;
        private List<WorkoutExercise> mExercises;
        private int mCurrentIndex;

        /**
         * Instantiates a {@link WorkoutDisplayer}
         * @param context The {@link Context} that this is created from
         * @param exercises List of {@link WorkoutExercise}'s that represent the exercises which this Displayer will go through
         */
        public WorkoutDisplayer(Context context,
                                List<WorkoutExercise> exercises) {
            super(context);
            mExercises = exercises;
            drawInitialViews(context);
            setOrientation(VERTICAL);
            mCurrentIndex = 0;
        }

        /**
         * TODO
         * Calculates how many items should be displayed based on the size of this layout
         * @return the maximum number of items to be displayed at one time
         */
        private void calculateNumberOfWorkoutsDisplayed(){
            MAX_NUMBER_EXERCISES_DISPLAYED = 3;
        }

        /**
         * Adds the Maximum number of possible fragments to the layout
         */
        public void drawInitialViews(Context context){
            calculateNumberOfWorkoutsDisplayed();
            for(int i = 0; i < MAX_NUMBER_EXERCISES_DISPLAYED; i++){
                RunExerciseView view = mExercises.get(i).generateRunExerciseFragment(context, this);
                this.addView(view);
            }
        }

        /**
         * TODO
         * What to do when a fragment is done with it's actions
         */
        @Override
        public void onExerciseDone() {
            startNextExercise();
        }

        public void startNextExercise(){
            try {
                //Remove the top view which is the exercise which just finished
                removeViewAt(0);
                int indexToAdd = mCurrentIndex + MAX_NUMBER_EXERCISES_DISPLAYED;
                //Check to make sure that List does not go out of bounds
                if (indexToAdd < mExercises.size()) {
                    //Add next view
                    this.addView(mExercises.get(indexToAdd).generateRunExerciseFragment(getContext(),
                            this));
                }
                //Increment the current index
                mCurrentIndex++;
                startExercise();
            }catch(NullPointerException e){
                //Do nothing
            }
        }

        public void startPreviousExercise(){
            //Check to make sure that the current item isn't the first one
            if(mCurrentIndex != 0) {
                //Check if remove of last child is needed
                if (getChildCount() == MAX_NUMBER_EXERCISES_DISPLAYED) {
                    //Remove the last view
                    int lastViewIndex = getChildCount() - 1;
                    removeViewAt(lastViewIndex);
                }
                //Resets the currently running exercise
                try {
                    ((RunExerciseView) getChildAt(0)).resetExercise();
                }catch(NullPointerException e){
                    //Do nothing
                }
                //Decrement the current index
                mCurrentIndex--;
                //add the previous view to the top
                RunExerciseView toAdd = mExercises.get(mCurrentIndex).
                        generateRunExerciseFragment(getContext(),this);
                addView(toAdd,0);
                toAdd.startExercise();
            }
        }

        /**
         * Starts the exercise at the top of this view
         */
        public void startExercise(){
            RunExerciseView toStart = ((RunExerciseView)getChildAt(0));
            if(toStart != null)
                    toStart.startExercise();
        }

        /**
         * Pauses the exercise at the top of this view
         */
        public void pauseExercise(){
            if(getChildCount()>0)
                ((RunExerciseView)getChildAt(0)).pauseExercise();
        }

        /**
         * Restarts the currently running exercise from the top
         */
        public void restartExercise(){
            RunExerciseView toRestart = (RunExerciseView) getChildAt(0);
            toRestart.resetExercise();
        }
    }

    private enum CurrentExerciseState{
        PAUSED, RUNNING
    }

}
