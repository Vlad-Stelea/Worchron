package vlad.backend.Exercises;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import vlad.Previews.PreviewFactory;


/**
 * Represents an exercise that can only be selected or created as a new type of exercise
 */
public class SelectableExercise implements Serializable, Comparable<SelectableExercise>{
    private String mName;

    public SelectableExercise(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    @Override
    public String toString(){
        return mName;
    }

    @Override
    public int compareTo(@NonNull SelectableExercise selectableExercise) {
        return this.toString().compareTo(selectableExercise.toString());
    }

    public static class SelectableExerciseFactory implements PreviewFactory<SelectableExercise> {

        @Override
        public SelectableExercise createT(String name) {
            return new SelectableExercise(name);
        }
    }
}
