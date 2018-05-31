package vlad.backend.Exercises;

import java.io.Serializable;

import vlad.Previews.PreviewFactory;


/**
 * Represents an exercise that can only be selected or created as a new type of exercise
 */
public class SelectableExercise implements Serializable{
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

    public static class SelectableExerciseFactory implements PreviewFactory<SelectableExercise> {

        @Override
        public SelectableExercise createT(String name) {
            return new SelectableExercise(name);
        }
    }
}
