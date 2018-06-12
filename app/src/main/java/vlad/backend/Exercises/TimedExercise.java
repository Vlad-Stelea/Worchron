package vlad.backend.Exercises;

/**
 * Represents an Exercise that has to last for a certain amount of time
 */
public class TimedExercise extends WorkoutExercise {
    //How long this exercise should last
    private long mTime;

    /**
     * Constructs a Timed Exercise object
     * @param exercise the {@link SelectableTimedExercise} that this will be a workout version of
     * @param time how long the exercise should last for
     */
    public TimedExercise(SelectableTimedExercise exercise, long time){
        super(exercise);
        mTime = time;
    }

    /**
     * gets the time of this exercise
     * @return the time of the exercise
     */
    public long getTime(){
        return mTime;
    }
}
