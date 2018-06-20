package vlad.worchron;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import vlad.backend.Exercises.RepExercise;
import vlad.backend.Exercises.SelectableExercise;
import vlad.backend.Exercises.SelectableTimedExercise;
import vlad.backend.Exercises.TimedExercise;
import vlad.backend.Exercises.WorkoutExercise;
import vlad.backend.Workout;

public class EditWorkout extends AppCompatActivity implements EditExerciseDialog.EditExerciseDialogCallback{
    //The exercises already in this workout
    private List<WorkoutExercise> mExercises;
    //Recycler view declarations
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton addButton, doneButton;
    private final static int PICK_EXERCISE_REQUEST_CODE = 1;
    private EditText nameField;
    private String workoutKey;
    //The position in the list of workouts that this is editing
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        //TODO change so that it gets a workout from the saved instanceState
        workoutKey = getString(R.string.edit_workout_workout_key);
        nameFieldInit();
        doneButtonInit();
        addButtonInit();
        Workout currentWorkout = (Workout) getIntent().getSerializableExtra(workoutKey);
        if(currentWorkout != null){
            nameField.setText(currentWorkout.getName());
            mExercises = currentWorkout.getExercises();
            editPosition = getIntent().getIntExtra(getString(R.string.edit_workout_position_key),-1);
        }else{
            mExercises = new ArrayList<>();
        }
        recyclerViewInit();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_EXERCISE_REQUEST_CODE){
                String workoutKey = getString(R.string.select_exercise_exercise_key);
                WorkoutExercise returnedExercise = (WorkoutExercise) data.getSerializableExtra(workoutKey);
                returnedExercise.getEditExerciseDialog().show(getFragmentManager(),"new_exercise");
                mExercises.add(returnedExercise);
            }
        }
    }
    //<-----------------------Initializer Methods--------------------->

    private void nameFieldInit(){
        nameField = findViewById(R.id.activity_edit_workout_name_field);
    }

    private void doneButtonInit(){
        doneButton = findViewById(R.id.activity_edit_workout_done_button);
        doneButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            String key = getString(R.string.edit_workout_result_code);
            Workout workout = new Workout(nameField.getText().toString(), mExercises);
            intent.putExtra(key,workout);
            intent.putExtra(getString(R.string.edit_workout_position_key), editPosition);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    private void addButtonInit(){
        addButton = findViewById(R.id.activity_edit_workout_add_button);
        addButton.setOnClickListener(view -> {
            //Start exercise selector activity for result of an exercise
            Intent intent = new Intent(this, SelectExerciseActivity.class);
            startActivityForResult(intent,PICK_EXERCISE_REQUEST_CODE);
        });
    }

    private void recyclerViewInit(){
        //get the recycler view from the layout
        mRecyclerView = findViewById(R.id.activity_edit_workout_recycler_view);
        //Set up the layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set up adaptor
        mAdapter = new MyAdaptor(mExercises);
        mRecyclerView.setAdapter(mAdapter);

    }
    //<-----------------------Callback stuff------------------------------->
    /**
     * Notifies the calling exercise that the exercise was changed
     */
    @Override
    public void sendEditedExercise() {
        mAdapter.notifyDataSetChanged();
    }


    //<---------------------Recycler View stuff--------------------------------------->
    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

        private List<WorkoutExercise> mExercises;

        public MyAdaptor(List<WorkoutExercise> exercises){
            mExercises = exercises;
        }

        @NonNull
        @Override
        public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ExerciseView view = new ExerciseView(parent.getContext());
            return new MyViewHolder(view);

        }
        /**
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ExerciseView view = (ExerciseView) holder.itemView;
            view.setExercise(mExercises.get(position));
        }

        @Override
        public int getItemCount(){
            return mExercises.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View itemView) {
                super(itemView);
            }

        }
    }


// <--------------ExerciseView stuff------------------------>

    /**
     * A View that displays an exercise
     */
    private class ExerciseView extends LinearLayout{
        //The current exercise to display
        private WorkoutExercise mCurrentExercise;
        private TextView mNameView, mTimeView, mSetsView, mRepsView;
        private View mRepViewContent;

        public ExerciseView(Context context) {
            super(context);
            inflate(context,R.layout.workout_exercise_layout,this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            setPadding(0,0,0,1);
            setLayoutParams(lp);
            setElevation(10);
            mNameView = this.findViewById(R.id.workout_exercise_layout_name);
            mTimeView = findViewById(R.id.workout_exercise_layout_time);
            mSetsView = findViewById(R.id.workout_exercise_layout_set_view);
            mRepsView = findViewById(R.id.workout_exercise_layout_rep_view);
            mRepViewContent = findViewById(R.id.workout_exercise_layout_rep_exercise_view);
            setOnClickListener(view -> {
                mCurrentExercise.getEditExerciseDialog().show(getFragmentManager(), "Edit");
            });
        }

        /**
         * Sets a new exercise for this view to display
         * @param newExercise the new exercise to display
         */
        public void setExercise(WorkoutExercise newExercise){
            mCurrentExercise = newExercise;
            mNameView.setText(newExercise.getName());
            //Reveal timed exercise stuff and set it up
            if(newExercise instanceof TimedExercise){
                mRepViewContent.setVisibility(GONE);
                mTimeView.setVisibility(VISIBLE);
                mTimeView.setText(((TimedExercise) newExercise).getTime());
            }else if(newExercise instanceof RepExercise){
                mTimeView.setVisibility(GONE);
                mRepViewContent.setVisibility(VISIBLE);
                RepExercise newEx = (RepExercise) newExercise;
                mRepsView.setText("" + newEx.getReps());
                mSetsView.setText(""+newEx.getSets());
            }

        }
    }

}
