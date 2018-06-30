package vlad.worchron;

import android.support.v4.app.Fragment;

public abstract class RunExerciseFragment extends Fragment {

    //The callback of the calling layout
    protected RunExerciseFragmentCallback mCallback;

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
