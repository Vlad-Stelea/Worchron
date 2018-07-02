package vlad.worchron;


import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import vlad.backend.Exercises.TimedExercise;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunTimedExerciseView extends RunExerciseView {
    private TimedExercise mExercise;
    private TextView mTimeView;
    private CountDownTimer mCountDownTimer;


    /**
     * Instantiates a new instance of a RunTimedExerciseFragment
     * @param context the {@link Context} that this is being called from
     * @param exercise the exercise to display
     * @param callback {@link vlad.worchron.RunExerciseView.RunExerciseFragmentCallback} where this is being created from
     */
    public RunTimedExerciseView(Context context,
                                TimedExercise exercise,
                                RunExerciseFragmentCallback callback){
        super(context, callback);
        mExercise = exercise;
        //Set up the GUI stuff for this View
        //inflate the correct layout
        inflate(context, R.layout.view_run_timed_exercise,this);
        //Set up the text view to display time
        mTimeView = findViewById(R.id.fragment_run_timed_exercise_time_view);
        //Set up the countdown timer
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

    }


//<----------------------Overriding methods---------------------------->
    /**
     * Lets the RunExerciseFragment know it should start doing it's thing
     */
    @Override
    public void startExercise() {
        mCountDownTimer.start();
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
