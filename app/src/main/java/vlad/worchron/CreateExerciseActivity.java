package vlad.worchron;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import vlad.backend.Exercises.Exercise;
import vlad.backend.Exercises.RepExercise;
import vlad.backend.Exercises.SelectableExercise;
import vlad.backend.Exercises.SelectableRepExercise;
import vlad.backend.Exercises.SelectableTimedExercise;

public class CreateExerciseActivity extends AppCompatActivity implements RenameDialog.RenameDialogCallback,
        RepExerciseCreatorFragment.RepExerciseCreatorCallback{

    private RadioGroup mRadioGroup;
    private View mRepView;
    private EditText mNameField;
    private RepExerciseCreatorFragment mRepCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        //Set up name field
        mNameField = findViewById(R.id.new_exercise_name);
        //Set up the RepView
        mRepView = findViewById(R.id.activity_create_exercise_fragment);
        mRepView.setVisibility(View.INVISIBLE);
        //Set up radiogroup
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener((radioGroup, checkedID)->{
          switch (checkedID){
              case R.id.rep_exercise_button:
                  mRepView.setVisibility(View.VISIBLE);
                  break;
               default:
                   mRepView.setVisibility(View.INVISIBLE);
                   break;
          }
        });
        findViewById(R.id.Create_Exercise_Done_Button).setOnClickListener(this::doneClicked);
        mRepCreator = (RepExerciseCreatorFragment) getSupportFragmentManager().findFragmentById(R.id.activity_create_exercise_fragment);

    }

    /**
     * Signals that this exercise has been created
     */
    private void doneClicked(View view){
        Intent intent = new Intent();
        String name = mNameField.getText().toString();
        if(name.length() > 0) {
            //if successful
            SelectableExercise newExercise;
            if (mRadioGroup.getCheckedRadioButtonId() == R.id.rep_exercise_button) {
                List<RepExercise.ExerciseStep> steps = mRepCreator.getSteps();
                newExercise = new SelectableRepExercise(name, steps);
            } else {
                newExercise = new SelectableTimedExercise(name);
            }
            String exerciseKey = getString(R.string.create_exercise_result);
            intent.putExtra(exerciseKey, newExercise);
            setResult(RESULT_OK, intent);
            finish();
        }else {
            //if name isn't filled in
            Toast.makeText(this,"Please fill in a name", Toast.LENGTH_LONG)
            .show();
        }
    }


//<-----------------------------------Callback stuff----------------------------------------------------------->
    private RepExerciseCreatorFragment currentSelectedExerciseCreator;

    /**
     * Callback that lets the activity know that the object has been renamed
     */
    @Override
    public void sendNewName() {
        currentSelectedExerciseCreator.renameStep();
    }

    /**
     * Does whatever it needs to if cancelled
     */
    @Override
    public void onCancel() {
        //Do nothing
    }

    @Override
    public void renameActivity(RepExerciseCreatorFragment fragment, RenameDialog.Renamable toRename) {
        currentSelectedExerciseCreator = fragment;
        RenameDialog dialog = RenameDialog.newInstance(toRename);
        dialog.show(getFragmentManager(), "Rename");
    }
}
