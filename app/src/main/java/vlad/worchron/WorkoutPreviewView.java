package vlad.worchron;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import vlad.Previews.WorkoutPreview;

public class WorkoutPreviewView extends LinearLayout {

    public WorkoutPreviewView(Context context, WorkoutPreview workoutPreview) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        TextView nameView = new TextView(context);
        nameView.setText(workoutPreview.getName());
        addView(nameView);
    }
}
