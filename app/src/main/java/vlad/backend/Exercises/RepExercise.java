package vlad.backend.Exercises;

import vlad.backend.WorkoutExercise;

/**
 * Class that represents an exercise that is done in reps eg. curls, pushups
 */
public class RepExercise extends WorkoutExercise {
    //The number of sets for this exercise
    private int mSets;
    //How many reps for each set
    private int mReps;
    //How long each stage should take for this exercise
    //If empty then this feature is not set up for the exercies
    private long[] mMetronomeTimes;

    /**
     * Constructs a RepExercises object
     * @param name the name of this exercise
     * @param sets how many sets of this exercise will occur
     * @param reps how many reps of this exercise will occur
     */
    public RepExercise(String name, int sets, int reps){
        super(name);
        mSets = sets;
        mReps = reps;
        mMetronomeTimes = new long[0];
    }

    /**
     * Constructs a RepExercise object
     * @param name the name of this exercise
     * @param sets the number of sets for this exercise
     * @param reps the number of reps for this exercise
     * @param metronomeTimes array of long values that represent how long each stage of the exercise has to execute for
     */
    public RepExercise(String name, int sets, int reps, long[] metronomeTimes){
        super(name);
        mSets = sets;
        mReps = reps;
        mMetronomeTimes = metronomeTimes;
    }
    @Override
    public String toString() {
        return null;
    }
}
