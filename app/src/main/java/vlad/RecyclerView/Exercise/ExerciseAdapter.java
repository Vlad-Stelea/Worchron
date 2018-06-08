package vlad.RecyclerView.Exercise;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vlad.Previews.ExercisePreview;
import vlad.backend.Algorithms.Sorters;
import vlad.backend.Exercises.SelectableExercise;
import vlad.worchron.R;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{
    private List<SelectableExercise> mDataSet;

    public ExerciseAdapter(List<SelectableExercise> dataSet){
        mDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    //TODO Test
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_preview_layout,parent,false);
        return new ViewHolder(view);
    }

    //TODO Test
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((TextView)holder.itemView.
                findViewById(R.id.exercise_name))
        .setText(mDataSet.get(position)
        .getName());
    }
    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    /**
     * Adds an element to the list at the correct position
     * @param toAdd The element which will be added to the list
     */
    public void addElement(SelectableExercise toAdd){
        Sorters.binaryInsert(mDataSet,toAdd);
        notifyDataSetChanged();
    }
}
