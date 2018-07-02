package vlad.worchron;


import android.app.Fragment;
import android.content.Context;
import android.widget.LinearLayout;

public abstract class RunExerciseView extends LinearLayout {

    //The callback of the calling layout
    protected RunExerciseFragmentCallback mCallback;

    public RunExerciseView(Context context, RunExerciseFragmentCallback callback) {
        super(context);
        mCallback = callback;
    }

    /**
     * Lets the RunExerciseFragment know it should start doing it's thing
     */
    public abstract void startExercise();
    /**
     * Interface to declare methods that interact with the layout that
     *  the fragment is contained in
     */
    public interface RunExerciseFragmentCallback{
        //notifies the implementing class that the fragment is done
        void onExerciseDone();
    }
}
