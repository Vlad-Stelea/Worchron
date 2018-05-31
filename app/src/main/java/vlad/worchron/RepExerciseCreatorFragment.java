package vlad.worchron;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlad.backend.Exercises.RepExercise;

/**
 * Fragment that adds steps to a rep exercise
 */
public class RepExerciseCreatorFragment extends Fragment {

    //How many steps this exercise will have
    private int mStepsCount;
    //The names of each step
    private List<RepExercise.ExerciseStep> mSteps;
    //Recycler view declarations
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RepExerciseCreatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RepExerciseCreatorFragment.
     */
    public static RepExerciseCreatorFragment newInstance() {
        RepExerciseCreatorFragment fragment = new RepExerciseCreatorFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rep_exercise_creator, container, false);
        TextView stepsDisplay = view.findViewById(R.id.exercise_step_num_view);
        mRecyclerView = view.findViewById(R.id.fragment_rep_exercise_creator_RecyclerView);

        mRecyclerView.setHasFixedSize(true);

        //Set up layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set up adaptor
        mAdapter = new MyAdaptor(mSteps);
        mRecyclerView.setAdapter(mAdapter);

        //Set up + Button
        view.findViewById(R.id.add_exercise_step_button).
                setOnClickListener(v->{
                    mStepsCount++;
                    stepsDisplay.setText("" + mStepsCount);
                    mSteps.add(new RepExercise.ExerciseStep("Change Name"));
                    mAdapter.notifyDataSetChanged();
        });
        //Set up - Button
        view.findViewById(R.id.subtract_exercise_step_button).
                setOnClickListener(v->{
                    if(mStepsCount > 0){
                        mStepsCount--;
                        stepsDisplay.setText(""+mStepsCount);
                        mSteps.remove(mSteps.size()-1);
                    }

        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStepsCount = 0;
        mSteps = new ArrayList<>();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * Gets all the steps of the newly created rep exercise
     * @return List of ExerciseSteps of a newly created exercise
     */
    public List<RepExercise.ExerciseStep> getSteps(){
        return mSteps;
    }

    private static class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{
        private List<RepExercise.ExerciseStep> mSteps;

        public MyAdaptor(List<RepExercise.ExerciseStep> steps){
            mSteps = steps;
        }

        /**
         */
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_step_layout,parent,false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        /**
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            TextView textView = holder.itemView.findViewById(R.id.exercise_step_layout_name);
            textView.setText(mSteps.get(position).getName());

            holder.itemView.setOnClickListener(view -> {
                Log.d("TEST","CLick");
            });
        }

        @Override
        public int getItemCount(){
            return mSteps.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View view){
                super(view);
            }
        }


    }

}
