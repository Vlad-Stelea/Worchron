package vlad.worchron;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import vlad.backend.Exercises.SelectableExercise;
import vlad.backend.Exercises.SelectableTimedExercise;

public class SelectExerciseActivity extends AppCompatActivity {
    private List<SelectableExercise> mExercises;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);
        mExercises = MainActivity.ExerciseDAO.loadAllBackend();
        recyclerViewInit();
    }

    private void recyclerViewInit(){
        //get the recycler view from the layout
        mRecyclerView = findViewById(R.id.activity_select_exercise_recycler_view);
        //Set up the layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set up adaptor
        mAdapter = new MyAdaptor(mExercises);
        mRecyclerView.setAdapter(mAdapter);

    }
    //<-------------------Recycler View Stuff-------------------------->
    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

        private List<SelectableExercise> mExercises;

        public MyAdaptor(List<SelectableExercise> exercises){
            mExercises = exercises;
        }

        @NonNull
        @Override
        public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ExercisePreviewView view = new ExercisePreviewView(parent.getContext());
            return new MyAdaptor.MyViewHolder(view);

        }
        /**
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ExercisePreviewView view = (ExercisePreviewView) holder.itemView;
            view.setOnClickListener(v ->{
                ExercisePreviewView exerciseView = (ExercisePreviewView) v;
                SelectableExercise exercise = exerciseView.mExercise;
                Intent intent = new Intent();
                String workoutKey = getString(R.string.select_exercise_exercise_key);
                intent.putExtra(workoutKey, exercise.createWorkoutVersion());
                setResult(RESULT_OK, intent);
                finish();
            });
            view.setExercise(mExercises.get(position));
        }

        @Override
        public int getItemCount(){
            return mExercises.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            //The current exercise view this view holder is holding
            private ExercisePreviewView mHolding;
            public MyViewHolder(ExercisePreviewView itemView) {
                super(itemView);
            }
        }
    }


}
