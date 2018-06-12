package vlad.worchron;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import vlad.backend.Exercises.SelectableTimedExercise;
import vlad.backend.Exercises.TimedExercise;
import vlad.backend.Exercises.WorkoutExercise;

public class EditWorkout extends AppCompatActivity {

    private List<WorkoutExercise> mExercises;
    //Recycler view declarations
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        //TODO change so that it gets a workout from the saved instanceState
        mExercises = new ArrayList<>();
        //Fill false arrayList
        for(int i = 0; i < 40; i++){
            mExercises.add(new TimedExercise(new SelectableTimedExercise("Run"),i+100));
        }
        addButton = findViewById(R.id.activity_edit_workout_add_button);
        recyclerViewInit();


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


    //<---------------------Recycler View stuff--------------------------------------->
    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

        private List<WorkoutExercise> mExercises;

        public MyAdaptor(List<WorkoutExercise> exercises){
            mExercises = exercises;
        }

        @NonNull
        @Override
        public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =  new ExerciseView(parent.getContext());
            return new MyViewHolder(view);

        }

        /**
         * TODO implement
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ((ExerciseView)holder.itemView).setContents(mExercises.get(position));
            holder.itemView.setOnClickListener(view -> ((ExerciseView)view).toggleCollapse());
        }

        @Override
        public int getItemCount(){
            return mExercises.size();
        }

        /**
         * View representation of a workout Exercise
         */
        private class ExerciseView extends FrameLayout{
            //The exercise that this view will display
            private WorkoutExercise mExercise;
            private boolean isCollapsed = true;
            private View root;
            private TextView mNameView;
            public ExerciseView(Context context) {
                super(context);
                inflate(context,R.layout.workout_exercise_collapsed,this);
                setLayoutParams(new LayoutParams(501,100));
                mNameView = this.findViewById(R.id.workout_exercise_name);
            }

            /**
             * Sets an exercise for the view
             * @param newExercise the new exercise this view should hold
             */
            public void setContents(WorkoutExercise newExercise){
                mExercise = newExercise;
                mNameView.setText(mExercise.getName());
                if(!isCollapsed){
                    //Do different stuff for Timed and rep exercises
                }
            }

            /**
             * toggles whether the view is collapsed or not
             */
            public void toggleCollapse(){
                if(isCollapsed)
                    expand();
                else
                    collapse();
                isCollapsed= !isCollapsed;
            }

            /**
             * TODO
             * collapses the view so only the name is visible
             */
            private void collapse(){

            }

            /**
             * TODO
             * expands   the view so that the user can modify the exercise's attributes
             */
            private void expand(){
                if(mExercise instanceof TimedExercise){
                    inflate(this.getContext(),R.layout.workout_exercise_timed_expanded,this);
                    mNameView = this.findViewById(R.id.workout_exercise_timed_expanded_name);
                    mNameView.setText(mExercise.getName());
                    ((TextView)this.findViewById(R.id.workout_exercise_timed_expanded_name)).setText("" + ((TimedExercise)mExercise).getTime());
                }
            }
        }

        /**
         * TODO implement
         */
        public class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
