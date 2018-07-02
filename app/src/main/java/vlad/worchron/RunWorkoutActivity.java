package vlad.worchron;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
        mainLayout = findViewById(R.id.activity_run_workout_layout);
        mWorkoutDisplayer = new WorkoutDisplayer(this, mExercises);
        mainLayout.addView(mWorkoutDisplayer);
        InitializeLayoutListener listener = new InitializeLayoutListener(mainLayout, mWorkoutDisplayer, this);
        mainLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(listener);
    }
    //<-----------------------Overriding methods--------------------------------->
    @Override
    public void onFinishDrawing(InitializeLayoutListener listener) {
        mainLayout.getViewTreeObserver()
                .removeOnGlobalLayoutListener(listener);
    }

    /*@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs){
        View view = super.onCreateView(parent, name, context, attrs);
        setWorkoutDisplayerDimensions(mainLayout, mWorkoutDisplayer);
        positionWorkoutDisplayer((ConstraintLayout) mainLayout);
        mWorkoutDisplayer.drawInitialViews(this);
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs){
        View view = this.onCreateView(null, name, context, attrs);
        return view;
    }*/




    /**
     * Layout responsible for Displaying and transitioning through a workout
     */
    public static class WorkoutDisplayer extends LinearLayout implements RunExerciseView.RunExerciseFragmentCallback{
        //The maximum number of exercises to show at one time
        private int MAX_NUMBER_EXERCISES_DISPLAYED;
        private List<WorkoutExercise> mExercises;

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
         * Removes all vies in the displayer and
         * Fills this Displayer with containers for fragments
         * AKA FrameLayouts
         */
        private void fillWithFrameLayouts(Context context){
            this.removeAllViews();
            for(int i = 0; i < MAX_NUMBER_EXERCISES_DISPLAYED; i++){
                FrameLayout toAdd = new FrameLayout(context);
                this.addView(toAdd);
                toAdd.getLayoutParams().width = LayoutParams.MATCH_PARENT;
                toAdd.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                toAdd.setId(i);
            }
        }

        /**
         * TODO
         * What to do when a fragment is done with it's actions
         */
        @Override
        public void onExerciseDone() {

        }

        private List<RunExerciseView> generateRunExerciseViewList(Context context, List<WorkoutExercise> exercises){
            ArrayList<RunExerciseView> toReturn = new ArrayList<>();
            //Loop through the exercises in the passed in list and convert them into the fragments
            for(WorkoutExercise exercise: exercises){
                toReturn.add(exercise.generateRunExerciseFragment(context,this));
            }
            return toReturn;
        }
    }

}
