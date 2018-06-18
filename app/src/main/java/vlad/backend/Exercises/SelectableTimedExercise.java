package vlad.backend.Exercises;

public class SelectableTimedExercise extends SelectableExercise {
    public SelectableTimedExercise(String name) {
        super(name);
    }

    @Override
    public WorkoutExercise createWorkoutVersion(){
        return new TimedExercise(this, 0);
    }
}
