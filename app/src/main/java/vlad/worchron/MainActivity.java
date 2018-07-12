package vlad.worchron;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vlad.DAO.GeneralDAO;
import vlad.DAO.WorkoutDAO;
import vlad.Previews.ExercisePreview;
import vlad.Previews.WorkoutPreview;
import vlad.backend.Exercises.Exercise;
import vlad.backend.Exercises.SelectableExercise;
import vlad.backend.Workout;

public class MainActivity extends AppCompatActivity {

    public static GeneralDAO<Workout,WorkoutPreview> WorkoutDAO;
    public static GeneralDAO<SelectableExercise, SelectableExercise> ExerciseDAO;
    private MainActivity thisActivity = this;
    private ImageButton addWorkoutButton;

    //Recycler View variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Workout> mWorkouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set up toolbar for this app
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up folder if it does not exist to store workouts
        String WORKOUT_DIR = getString(R.string.workout_dir);
        String EXERCISE_DIR = getString(R.string.exercise_dir);
        String firstTimeAccessKey = "firstTime";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        WorkoutDAO = new GeneralDAO<>(this, WORKOUT_DIR, new WorkoutPreview.WorkoutPreviewFactory());
        ExerciseDAO = new GeneralDAO<>(this, EXERCISE_DIR, new SelectableExercise.SelectableExerciseFactory());
        if(!prefs.getBoolean(firstTimeAccessKey,false)){
            boolean workoutInit = WorkoutDAO.initializeDirectory();
            boolean exerciseInit = ExerciseDAO.initializeDirectory();
            if(workoutInit && exerciseInit) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(firstTimeAccessKey, true);
                editor.apply();
            }
        }
        RecyclerViewInit();
        addWorkoutButton = findViewById(R.id.activity_main_add_workout_button);
        addWorkoutButton.setOnClickListener(this::addNewWorkout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        //TODO Add the logo of the app
        //getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == getResources().getInteger(R.integer.new_workout_request_code)){
                //a completely new workout is returned
                Workout workout = (Workout) data.getSerializableExtra(getString(R.string.edit_workout_result_code));
                WorkoutDAO.saveBackend(workout,this);
                //TODO insert the new workout into the list of workouts
            }else if(requestCode == getResources().getInteger(R.integer.edit_workout_request_code)){
                //edit an existing exercise
                Workout workout = (Workout) data.getSerializableExtra(getString(R.string.edit_workout_result_code));
                int position = data.getIntExtra(getString(R.string.edit_workout_position_key), -1);
                //TODO modify the list if it is changed
                WorkoutDAO.saveBackend(workout,null);
            }

        }
    }

    //<-----------------------------Init methods------------------------>
    private void RecyclerViewInit(){
        //set up the list of workouts
        mWorkouts = WorkoutDAO.loadAllBackend();

        //find the right recyclerview
        mRecyclerView = findViewById(R.id.activity_main_workout_displayer);

        //Set up the layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set up adaptor
        mAdapter = new MyAdaptor(mWorkouts);
        mRecyclerView.setAdapter(mAdapter);

    }

    //<--------------------RecyclerView stuff---------------------------------------->
    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

        private List<Workout> mWorkouts;

        public MyAdaptor(List<Workout> exercises){
            mWorkouts = exercises;
        }

        @NonNull
        @Override
        public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WorkoutPreviewView view = new WorkoutPreviewView(thisActivity);
            return new MyViewHolder(view);
        }
        /**
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyAdaptor.MyViewHolder holder, int position) {
            WorkoutPreviewView view = (WorkoutPreviewView) holder.itemView;
            view.setWorkout(mWorkouts.get(position));
            view.setOnLongClickListener(v ->{
                Intent intent = new Intent(thisActivity, EditWorkout.class);
                String key = getString(R.string.edit_workout_workout_key);
                int requestCode = getResources().getInteger(R.integer.edit_workout_request_code);
                intent.putExtra(key,view.getCurrentWorkout());
                intent.putExtra(getString(R.string.edit_workout_position_key), position);
                thisActivity.startActivityForResult(intent, requestCode);
                return true;
            });
        }

        @Override
        public int getItemCount(){
            return mWorkouts.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View itemView) {
                super(itemView);
            }

        }
    }
//<-------------------WorkoutPreviewView stuff--------------------------------------->

    private static class WorkoutPreviewView extends LinearLayout {
        private TextView nameText;
        private Workout currentWorkout;
        public WorkoutPreviewView(Context context) {
            super(context);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(lp);
            LayoutInflater.from(context).inflate(R.layout.workout_preview_view_layout,this);
            setPadding(0,0,0,1);
            nameText = this.findViewById(R.id.workout_preview_view_layout_workout_name);
            setOnClickListener(view -> {
                //Start the RunWorkoutActivity with the current workout passed in as a parameter
                Intent intent = new Intent(getContext(),RunWorkoutActivity.class);
                intent.putExtra(getContext().getString(R.string.run_workout_workout_key),
                        currentWorkout);
                getContext().startActivity(intent);
            });
        }

        public void setWorkout(Workout newWorkout){
            currentWorkout = newWorkout;
            nameText.setText(currentWorkout.getName());
        }

        public Workout getCurrentWorkout(){
            return currentWorkout;
        }
    }

    //<-------------------------ActionListener Events--------------------->
    private void addNewWorkout(View view){
        Intent intent = new Intent(this, EditWorkout.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.new_workout_request_code));
    }

}
