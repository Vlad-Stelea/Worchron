package vlad.worchron;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

import vlad.DAO.GeneralDAO;

import vlad.backend.Algorithms.Sorters;
import vlad.backend.Exercises.WorkoutExercise;
import vlad.backend.Workout;
import vlad.Previews.WorkoutPreview;


/**
 */
public class WorkoutsFragment extends Fragment {
    private GeneralDAO workoutDAO = MainActivity.WorkoutDAO;
    private static final int CREATE_WORKOUT_REQUEST = 1;
    public static final String EXERCISE_LIST_CODE = "ExerciseList";
    //Recycler View variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Workout> mWorkouts;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance() {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        mWorkouts = workoutDAO.loadAllBackend();
        //Set up button click
        ImageButton createWorkoutButton = view.findViewById(R.id.add_workout_button);
        createWorkoutButton.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), EditWorkout.class);
            getActivity().startActivityForResult(intent, getResources().getInteger(R.integer.new_workout_request_code));
        });
        //get the recycler view from the layout
        mRecyclerView = view.findViewById(R.id.fragment_workouts_recycler_view);
        RecyclerViewInit();
        return view;
    }

    /**
     * Add a workout to this view
     * @param workout the workout to be added
     */
    public void addWorkout(Workout workout){
        Sorters.binaryInsert(mWorkouts, workout);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Let the fragment know that a workout was changed in some way
     */
    public void notifyWorkoutChanged(Workout toUpdate, int position){
        mWorkouts.set(position,toUpdate);
        mAdapter.notifyDataSetChanged();
    }
    //<---------------Initializers----------------------------------->

    private void RecyclerViewInit(){
        //Set up the layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set up adaptor
        mAdapter = new MyAdaptor(mWorkouts);
        mRecyclerView.setAdapter(mAdapter);

    }

    //<----------------Recycler View stuff--------------------------->
    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

        private List<Workout> mWorkouts;

        public MyAdaptor(List<Workout> exercises){
            mWorkouts = exercises;
        }

        @NonNull
        @Override
        public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WorkoutPreviewView view = new WorkoutPreviewView(parent.getContext());
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
                Intent intent = new Intent(getActivity(), EditWorkout.class);
                String key = getString(R.string.edit_workout_workout_key);
                int requestCode = getResources().getInteger(R.integer.edit_workout_request_code);
                intent.putExtra(key,view.getCurrentWorkout());
                intent.putExtra(getString(R.string.edit_workout_position_key), position);
                getActivity().startActivityForResult(intent, requestCode);
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

    private static class WorkoutPreviewView extends LinearLayout{
        private TextView nameText;
        private Workout currentWorkout;
        public WorkoutPreviewView(Context context) {
            super(context);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(lp);
            LayoutInflater.from(context).inflate(R.layout.workout_preview_view_layout,this);
            setPadding(0,0,0,1);
            nameText = this.findViewById(R.id.workout_preview_view_layout_workout_name);
        }

        public void setWorkout(Workout newWorkout){
            currentWorkout = newWorkout;
            nameText.setText(currentWorkout.getName());
        }

        public Workout getCurrentWorkout(){
            return currentWorkout;
        }
    }

}
