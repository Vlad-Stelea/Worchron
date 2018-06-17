package vlad.worchron;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import vlad.backend.Exercises.SelectableExercise;

public class ExercisePreviewView extends LinearLayout {
    SelectableExercise mExercise;
    TextView mNameView;
    /**
     * Creates an ExercisePreviewView for
     * @param context the context that this view is created from
     */
    public ExercisePreviewView(Context context) {
        super(context);
        //Set the size of this view
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setPadding(0,0,0,1);
        inflate(context,R.layout.exercise_preview_view_layout, this);
        mNameView = findViewById(R.id.exercise_preview_view_layout_name);
    }

    public void setExercise(SelectableExercise exercise){
        mExercise = exercise;
        mNameView.setText(mExercise.getName());
    }
}
