package vlad.backend.Exercises;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import vlad.worchron.EditExerciseDialog;
import vlad.worchron.R;
import vlad.worchron.RenameDialog;
import vlad.worchron.RunExerciseView;
import vlad.worchron.RunRepExerciseView;

/**
 * Class that represents an exercise that is done in reps eg. curls, pushups
 */
public class RepExercise extends WorkoutExercise {
    //The number of sets for this exercise
    private int mSets;
    //How many reps for each set
    private int mReps;

    /**
     * Constructs a RepExercises object
     * @param repExercise the exercise that this will be a version of
     * @param sets how many sets of this exercise will occur
     * @param reps how many reps of this exercise will occur
     */
    public RepExercise(SelectableRepExercise repExercise, int sets, int reps){
        super(repExercise);
        mSets = sets;
        mReps = reps;
    }

    public int getSets(){
        return mSets;
    }

    public int getReps(){
        return mReps;
    }

    /**
     * Creates an edit exercise dialog so that the user can edit the exercise
     * @return
     */
    @Override
    public EditExerciseDialog getEditExerciseDialog() {
        return RepExerciseEditDialog.newInstance(this);
    }

    /**
     * Converts the workout exercise that this is called from into the corresponding {@link RunExerciseView}
     * TODO
     * @param context  the context from where this is called
     * @param callback {@link RunExerciseView.RunExerciseFragmentCallback} Class that implements the correct callback so the fragment can interact with it
     * @return The correct {@link RunExerciseView} that represents this exercise and should be run
     */
    @Override
    public RunExerciseView generateRunExerciseFragment(Context context, RunExerciseView.RunExerciseFragmentCallback callback) {
        return new RunRepExerciseView(context,this, callback);
    }


    //<-------------------EditDialogStuff-------------------------------->

    public static class RepExerciseEditDialog extends EditExerciseDialog{
        private static String EXERCISE_KEY = "Exercise";

        private static RepExerciseEditDialog newInstance(RepExercise exercise){
            RepExerciseEditDialog dialog = new RepExerciseEditDialog();
            Bundle args = new Bundle();
            args.putSerializable(EXERCISE_KEY,exercise);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            RepExercise toEdit = (RepExercise) getArguments().get(EXERCISE_KEY);
            View view = getActivity().getLayoutInflater().inflate(R.layout.rep_exercise_edit_dialog_layout, null);
            //Set up Number pickers
            NumberPicker setPicker = view.findViewById(R.id.rep_exercise_edit_dialog_layout_sets);
            NumberPicker repPicker = view.findViewById(R.id.rep_exercise_edit_dialog_layout_reps);
            setPicker.setMinValue(0);
            repPicker.setMinValue(0);
            setPicker.setMaxValue(900);
            repPicker.setMaxValue(900);
            setPicker.setValue(toEdit.getSets());
            repPicker.setValue(toEdit.getReps());
            //Set up builder parameters
            builder.setTitle("Edit Reps and Sets")
            .setView(view)
            .setNegativeButton("Cancel", (dialog, id) ->{
              dismiss();
            })
            .setPositiveButton("Ok", (dialog, id) ->{
                toEdit.mReps = repPicker.getValue();
                toEdit.mSets = setPicker.getValue();
                mCallback.sendEditedExercise();
            });
            return builder.create();
        }
    }

//<-----------------------Exercise Step stuff----------------------------->
    public static class ExerciseStep implements RenameDialog.Renamable{
        private String mName;

        public ExerciseStep(String name){
            mName = name;
        }

        public String getName(){
            return mName;
        }

        /**
         * Sets a new name for the object
         *
         * @param newName the objects new name
         */
        @Override
        public void rename(String newName) {
            mName = newName;
        }
    }
}
