package vlad.backend.Exercises;

import java.util.ArrayList;
import java.util.List;

public class SelectableRepExercise extends SelectableExercise {
    private List<RepExercise.ExerciseStep> mSteps;
    ;

    public SelectableRepExercise(String name, List<RepExercise.ExerciseStep> steps) {
        super(name);
        mSteps = steps;
    }
}
