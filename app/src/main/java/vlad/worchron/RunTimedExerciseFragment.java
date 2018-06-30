package vlad.worchron;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vlad.backend.Exercises.TimedExercise;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunTimedExerciseFragment extends RunExerciseFragment {
    private TimedExercise mExercise;
    private TextView mTimeView;
    private CountDownTimer mCountDownTimer;


    public RunTimedExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Instantiates a new instance of a RunTimedExerciseFragment
     * @param exercise the exercise to display
     * @param callingLayout {@link vlad.worchron.RunExerciseFragment.RunExerciseFragmentCallback} where this is being created from
     * @return A new {@link RunTimedExerciseFragment} with proper parameters initialized
     */
    public static RunTimedExerciseFragment newInstance(TimedExercise exercise, RunExerciseFragmentCallback callingLayout){
        RunTimedExerciseFragment toReturn = new RunTimedExerciseFragment();
        toReturn.mExercise = exercise;
        toReturn.mCallback = callingLayout;
        return toReturn;
    }
//<----------------------Overriding methods---------------------------->
    /**
     * Lets the RunExerciseFragment know it should start doing it's thing
     */
    @Override
    public void startExercise() {
        mCountDownTimer.start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_run_timed_exercise, container, false);
        //Set up the correct text Views
        mTimeView = view.findViewById(R.id.fragment_run_timed_exercise_time_view);
        mCountDownTimer = new CountDownTimer(mExercise.getTimeInMillis(), 100) {
            @Override
            public void onTick(long timeLeft) {
                displayTime(timeLeft);
            }

            @Override
            public void onFinish() {
                mCallback.onExerciseDone();
            }
        };
        return view;
    }
//<-------------------------Time formatting ------------------------------------>
    /**
     * Takes in a time in milliseconds and formats the textviews to display the correct time
     * @param time the time to display
     */
    private void displayTime(long time){
        String toDisplay = convertMilliToDisplayString(time);
        mTimeView.setText(toDisplay);
    }

    /**
     * @param time A long that represents how many milliseconds this needs to be converted to
     * @return a formatted {@link String} of how the time should be displayed.
     * If there are 0 hours removes the hours digits and colon
     * Same for minutes
     */
    private String convertMilliToDisplayString(long time){
        //Convert the time to seconds
        int reducedTime = (int) (time/1000);
        //Define string formats
        final String hoursDisplayedFormatString = "%02d:%02d:%02d";
        final String minutesDisplayedFormatString = "%02d:%02d";
        //Convert seconds to corresponding hours, minutes and seconds values
        int hours = reducedTime/3600;
        int minutes = reducedTime/60 - hours*60;
        int seconds = reducedTime%60;
        //Define the string that will be returned
        String toReturn;
        //takes out the hours stuff if there are 0 hours
        if(hours != 0){
            toReturn = String.format(hoursDisplayedFormatString,hours,minutes,seconds);
        }else if(minutes != 0){//takes out minutes if there are no hours and minutes
            toReturn = String.format(minutesDisplayedFormatString, minutes, seconds);
        }else{//There are only seconds left
            toReturn = String.valueOf(seconds);
        }
        return toReturn;
    }


}
