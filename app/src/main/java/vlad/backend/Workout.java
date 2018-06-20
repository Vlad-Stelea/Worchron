package vlad.backend;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import vlad.backend.Exercises.WorkoutExercise;

/**
 * Represents a workout which is a collection of exercises
 */
public class Workout implements Serializable, Comparable<Workout>{
    //The name of the workout eg. Cardio, Leg day etc.
    private String mName;
    //The exercises contained in this workout
    private List<WorkoutExercise> mQueue;

    /**
     * Initializes a workout object
     * @param name the name of the workout
     */
    public Workout(String name, List<WorkoutExercise> exercises){
        this.mName = name;
        mQueue = exercises;
    }

    public List<WorkoutExercise> getExercises(){
        return mQueue;
    }
    /**
     * gets the name of the workout
     * @return the name of the workout object
     */
    public String getName(){
        return mName;
    }

    @Override
    public int compareTo(@NonNull Workout workout) {
        return this.mName.compareTo(workout.mName);
    }
}
