package vlad.backend.Exercises;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SelectableRepExercise extends SelectableExercise {
    private List<RepExercise.ExerciseStep> mSteps;

    @Override
    public WorkoutExercise createWorkoutVersion(){
        return new RepExercise(this,0,0);
    }

    public SelectableRepExercise(String name, List<RepExercise.ExerciseStep> steps) {
        super(name);
        mSteps = steps;
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        customWrite(out);
        out.writeObject(mSteps);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        customRead(in);
        mSteps = (List<RepExercise.ExerciseStep>) in.readObject();
    }
}
