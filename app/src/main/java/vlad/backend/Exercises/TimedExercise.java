package vlad.backend.Exercises;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.R;

/**
 * Represents an Exercise that has to last for a certain amount of time
 */
public class TimedExercise extends WorkoutExercise {
    //How long this exercise should last
    private long mTime;

    /**
     * Constructs a Timed Exercise object
     * @param exercise the {@link SelectableTimedExercise} that this will be a workout version of
     * @param time how long the exercise should last for
     */
    public TimedExercise(SelectableTimedExercise exercise, long time){
        super(exercise);
        mTime = time;
    }

    /**
     * gets the time of this exercise
     * @return the time of the exercise
     */
    public String getTime(){
        int h, m, s;
        h = getHours();
        m = getMinutes();
        s = getSeconds();
        return String.format("%02d:%02d:%02d",h,m,s);
    }

    /**
     * Converts the time into hours only
     * @return how many hours this exercise takes
     */
    public int getHours(){
        return (int)(mTime/3600);
    }

    /**
     * Converts the time into minutes
     * @return how many minutes this exercise will take
     */
    public int getMinutes(){
        return (int)(mTime/60 - getHours()*60);
    }

    /**
     * Converts the exercise into seconds
     * @return how many seconds this exercise will take
     */
    public int getSeconds(){
        return (int)(mTime % 60);
    }


    /**
     * Creates an edit exercise dialog so that the user can edit the exercise
     * @return
     */
    @Override
    public EditExerciseDialog getEditExerciseDialog() {
        return TimedExerciseEditDialog.newInstance(this);
    }

    public static class TimedExerciseEditDialog extends EditExerciseDialog{

        private static String EXERCISE_KEY = "Exercise";


        public static TimedExerciseEditDialog newInstance(TimedExercise exercise){
            TimedExerciseEditDialog dialog = new TimedExerciseEditDialog();
            Bundle args = new Bundle();
            args.putSerializable(EXERCISE_KEY,exercise);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            TimedExercise toEdit = (TimedExercise) getArguments().get(EXERCISE_KEY);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.timed_exercise_edit_dialog_layout,null);
            //Set up time picker widgets
            NumberPicker hours,minutes,seconds;
            hours = view.findViewById(R.id.timed_exercise_edit_dialog_hours);
            minutes = view.findViewById(R.id.timed_exercise_edit_dialog_minutes);
            seconds = view.findViewById(R.id.timed_exercise_edit_dialog_seconds);
            hours.setMinValue(0);
            hours.setMaxValue(23);
            minutes.setMinValue(0);
            minutes.setMaxValue(59);
            seconds.setMinValue(0);
            seconds.setMaxValue(59);
            //Set values to previous exercise values
            hours.setValue(toEdit.getHours());
            minutes.setValue(toEdit.getMinutes());
            seconds.setValue(toEdit.getSeconds());
            //Set up builder settings
            builder.setTitle("Please enter a new time for " + toEdit.getName())
            .setView(view)
            .setNegativeButton("Cancel", ((dialogInterface, i) -> {
                dismiss();
            }))
            .setPositiveButton("OK", (dialog, id) ->{
                toEdit.mTime = createTime(hours.getValue(),
                        minutes.getValue(),
                        seconds.getValue());
                mCallback.sendEditedExercise();
            });

            return builder.create();
        }

        /**
         * converts an input of hours minutes and seconds into seconds
         * @param h hours
         * @param m minutes
         * @param s seconds
         * @return a long of how many seconds this is
         */
        private static long createTime(int h, int m, int s){
            return (long)s + (60 * (long)m) + (3600 * (long)h);
        }
    }
}