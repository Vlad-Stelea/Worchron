package vlad.worchron;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;



public class InitializeLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{
    private ViewGroup mMainLayout;
    private RunWorkoutActivity.WorkoutDisplayer mWorkoutDisplayer;
    private FinishInitializeCallback mCallback;
    private static final int WORKOUT_DISPLAYER_ID = 100;

    /**
     * Initialize this layout listener for one time use of organizing this view
     * @param mainLayout {@link ViewGroup}
     * @param workoutDisplayer {@link RunWorkoutActivity.WorkoutDisplayer} to be positioned
     * @param callback {@link FinishInitializeCallback} the callback that this will call when done to remove this LayoutListener
     */
    public InitializeLayoutListener(ViewGroup mainLayout,
                                    RunWorkoutActivity.WorkoutDisplayer workoutDisplayer,
                                    FinishInitializeCallback callback){
        mMainLayout = mainLayout;
        mWorkoutDisplayer = workoutDisplayer;
        mCallback = callback;
    }

    @Override
    public void onGlobalLayout() {
        setWorkoutDisplayerDimensions(mMainLayout, mWorkoutDisplayer);
        positionWorkoutDisplayer((ConstraintLayout) mMainLayout);
        mCallback.onFinishDrawing(this);
    }

    /**
     * Sets the workoutdisplayer to the right dimensions so it looks good
     * TODO fix height detection
     * @param mainLayout The main layout of the activity
     * @param displayer {@link RunWorkoutActivity.WorkoutDisplayer} the displayer that needs to get sized
     */
    private void setWorkoutDisplayerDimensions(ViewGroup mainLayout, RunWorkoutActivity.WorkoutDisplayer displayer){
        final int margins = 100;
        int height = mainLayout.getHeight();
        Log.d("TEST","Original Height is" + height);
        //Loop through all the children and subtract from the total height to find how much space this displayer has
        for(int i = 0; i < mainLayout.getChildCount(); i++){
            height -= mainLayout.getChildAt(i).getHeight();
        }
        Log.d("TEST","Final Height is" + height);
        displayer.getLayoutParams().height = 1731;//TODO change
        displayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Constrains the workoutdisplayer to display as the last object to be displayed
     * @param layout the layout to be edited
     */
    private void positionWorkoutDisplayer(ConstraintLayout layout){
        mWorkoutDisplayer.setId(WORKOUT_DISPLAYER_ID);
        final int TOP_MARGIN = 5;
        final int SIDE_MARGIN = 20;
        final int BOTTOM_MARGIN = 5;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        //Connect the top of the workoutdisplayer to bottom of workout name
        constraintSet.connect(mWorkoutDisplayer.getId(),
                ConstraintSet.TOP, R.id.activity_run_workout_name_view,
                ConstraintSet.BOTTOM,
                TOP_MARGIN);
        //Connect sides
        constraintSet.connect(mWorkoutDisplayer.getId(),
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                SIDE_MARGIN);
        constraintSet.connect(mWorkoutDisplayer.getId(),
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                SIDE_MARGIN);
        //Connect bottom
       /*constraintSet.connect(mWorkoutDisplayer.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                BOTTOM_MARGIN);*/
        constraintSet.applyTo(layout);
    }
    //<---------------------Callback declaration----------------------------------------->
    public interface FinishInitializeCallback{
        void onFinishDrawing(InitializeLayoutListener listener);
    }
}
