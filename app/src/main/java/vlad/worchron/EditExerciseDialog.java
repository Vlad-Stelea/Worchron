package vlad.worchron;

import android.app.Activity;
import android.app.DialogFragment;

public abstract class EditExerciseDialog extends DialogFragment {
    protected EditExerciseDialogCallback mCallback;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        //Make sure that this activity has the proper callbacks
        if(!(activity instanceof EditExerciseDialogCallback)){
            throw new ClassCastException(activity.toString() + " does not implement EditExerciseDialogCallback");
        }
        mCallback = (EditExerciseDialogCallback) activity;
    }

    //<---------------Callback stuff--------------------------->

    /**
     * TODO inteface that ensures that the activity that calls it can receive changes
     */
    public interface EditExerciseDialogCallback{
        /**
         * Notifies the calling exercise that the exercise was changed
         */
        public void sendEditedExercise();
    }
}
