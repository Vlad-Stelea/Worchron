package backend;

/**
 * Represents an Exercise that has to last for a certain amount of time
 */
public class TimedExercise extends WorkoutExercise {
    //How long this exercise should last
    private long mTime;

    /**
     * Constructs a Timed Exercise object
     * @param name the name of the exercise
     * @param time how long the exercise should last for
     */
    public TimedExercise(String name, long time){
        super(name);
        mTime = time;
    }
    @Override
    public String toString() {
        return null;
    }
}
