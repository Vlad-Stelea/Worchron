package vlad.backend.Exercises;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import vlad.Previews.PreviewFactory;


/**
 * Represents an exercise that can only be selected or created as a new type of exercise
 * Reminder that all extending must implement writeObject and readObject for custom serialization
 */
public class SelectableExercise implements Serializable, Comparable<SelectableExercise>{
    //Constant to allow me to modify this class without previous saved files being weird
    private static final long serialVersionUID = 1L;
    //Instance Variables
    private String mName;
    //The image associated with the exercises
    private Bitmap mImage;

    public SelectableExercise(String name){
        mName = name;
    }

    public void setImage(Bitmap newImage){
        mImage = newImage;
    }

    public Bitmap getImage(){
        return mImage;
    }

    public String getName(){
        return mName;
    }

    public WorkoutExercise createWorkoutVersion() {
        (new CantCreateWorkoutVersionException(this)).printStackTrace();
        System.exit(1);
        return null;
    }

    @Override
    public String toString(){
        return mName;
    }

    @Override
    public int compareTo(@NonNull SelectableExercise selectableExercise) {
        return this.toString().compareTo(selectableExercise.toString());
    }

    /**TODO
     * Takes in an {@link ObjectOutputStream} and does the basic stuff needed to write this to a file
     * @param out the {@link ObjectOutputStream} where all the variables will be written
     */
    protected void customWrite(ObjectOutputStream out) throws IOException{
        //Write the name of the exercise
        out.writeObject(mName);
        //Write the image to the output stream
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        mImage.compress(Bitmap.CompressFormat.PNG,
                100,
                byteStream);
        byte[] byteArray = byteStream.toByteArray();
        out.write(byteArray.length);
        out.write(byteArray);
    }

    /**
     * Takes in an {@link ObjectInputStream} and reads the stored instance variables for a SelectableExercise
     * @param in The {@link ObjectInputStream} where all the variables will be read from
     */
    protected void customRead(ObjectInputStream in) throws IOException, ClassNotFoundException{
        this.mName = (String) in.readObject();
        int length = in.read();
        byte[] toDecode = new byte[length];
        in.read(toDecode);
        mImage = BitmapFactory.decodeByteArray(toDecode,0,length);
    }

    public static class SelectableExerciseFactory implements PreviewFactory<SelectableExercise> {
        @Override
        public SelectableExercise createT(String name) {
            return new SelectableExercise(name);
        }
    }

    public static class CantCreateWorkoutVersionException extends Exception{
        public CantCreateWorkoutVersionException(SelectableExercise exercise){
            super(exercise.getName() + " can not create a workout version of this exercise. \nYou should implement createWorkoutVersion() in the calling exercise.");
        }
    }
}
