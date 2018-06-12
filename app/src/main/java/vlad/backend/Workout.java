package vlad.backend;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import vlad.backend.Exercises.WorkoutExercise;

/**
 * Represents a workout which is a collection of exercises
 */
public class Workout implements Serializable{
    //The name of the workout eg. Cardio, Leg day etc.
    private String mName;
    //The location where this workout will be saved
    private List<WorkoutExercise> mQueue;

    /**
     * Initializes a workout object
     * @param name the name of the workout
     */
    public Workout(String name){
        this.mName = name;
        mQueue = new LinkedList<>();
    }

    /**
     * gets the name of the workout
     * @return the name of the workout object
     */
    public String getName(){
        return mName;
    }

}
