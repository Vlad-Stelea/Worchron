package backend;

import java.util.ArrayList;

/**
 * Represents a workout which is a collection of exercises
 */
public class Workout {
    //The name of the workout eg. Cardio, Leg day etc.
    private String mName;
    //List of all the exercises which will happen in this exercise
    private ArrayList<Exercise> exercises;

    /**
     * Initializes a workout object
     * @param name the name of the workout
     */
    public Workout(String name){
        this.mName = name;
        exercises = new ArrayList<>();
    }

    /**
     *
     * @return the name that will be displayed when selecting a workout
     */
    public String toString(){
        return mName;
    }

    /**
     * gets the list of exercises in this workout
     * @return what exercise this workout contains
     */
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
}
