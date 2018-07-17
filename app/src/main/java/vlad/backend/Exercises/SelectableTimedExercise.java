package vlad.backend.Exercises;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SelectableTimedExercise extends SelectableExercise {
    public SelectableTimedExercise(String name) {
        super(name);
    }

    @Override
    public WorkoutExercise createWorkoutVersion(){
        return new TimedExercise(this, 0);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        customWrite(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        customRead(in);
    }
}
