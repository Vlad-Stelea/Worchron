package vlad.worchron;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import vlad.backend.Exercises.RepExercise;

public class RunRepExerciseView extends RunExerciseView {
    private RepExercise mExercise;
    private int mSetsLeft;
    private TextView mNameView, mSetView, mRepView;
    private boolean mIsStarted;

    public RunRepExerciseView(Context context,
                              RepExercise exercise,
                              RunExerciseFragmentCallback callback) {
        super(context, callback);
        //set up local non-gui variables
        mExercise = exercise;
        mSetsLeft = mExercise.getSets();
        //set up the gui
        //inflates the layout
        inflate(context,R.layout.view_run_rep_exercise, this);
        mNameView = findViewById(R.id.view_run_rep_exercise_name);
        mSetView = findViewById(R.id.view_run_rep_exercise_sets);
        mRepView = findViewById(R.id.view_run_rep_exercise_reps);
        mNameView.setText(mExercise.getName());
        updateSets(mSetsLeft);
        updateReps(mExercise.getReps());
        setOnClickListener(this::onTouch);
    }

    /**
     * Lets the RunExerciseFragment know it should start doing it's thing
     */
    @Override
    public void startExercise() {
        mIsStarted = true;
    }

    /**
     * Pauses the exercise
     */
    @Override
    public void pauseExercise() {
        mIsStarted = false;
    }

    @Override
    public void resetExercise() {
        mSetsLeft = mExercise.getSets();
        updateSets(mSetsLeft);
    }

    private void onTouch(View view){
        if(mIsStarted) {
            mSetsLeft--;
            updateSets(mSetsLeft);
            if(mSetsLeft == 0){
                mCallback.onExerciseDone();
            }
        }
    }

    //<-----------------------------Gui update methods-------------------------->

    /**
     * Updates the repview to display the new reps
     * @param newReps the new number of reps to update
     */
    private void updateReps(int newReps){
        mRepView.setText("Reps: " + newReps);
    }

    /**
     * Updates the setview to display the new sets
     * @param newSets the new number of reps to update
     */
    private void updateSets(int newSets){
        mSetView.setText("Sets: " + newSets);
    }
}
