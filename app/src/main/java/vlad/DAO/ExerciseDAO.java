package vlad.DAO;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import vlad.backend.Exercises.SelectableExercise;
import vlad.backend.Exercises.SelectableTimedExercise;

/**
 * A DataAccessObject built specifically for SelectableExercises
 *  -Responsible for the reading and writing of objects into the file system
 *  File name structure = name_of_exercise.wrk
 *      -(eg.) Pushup.wrk, Run.wrk
 */
public class ExerciseDAO {
    //Directory where Backend will be saved
    private final File mSAVE_DIR;
    //Directory where the pictures for Backend will be saved
    private final File mPIC_DIR;

    /**
     * Creates a new ExerciseDAO for loading and saving exercises
     * @param context the context that this is being created from
     * @param saveDir the directory that exercises will be saved in
     * @param picDir the directory that pictures will be saved in
     */
    public ExerciseDAO(Context context, String saveDir, String picDir){
        mSAVE_DIR = new File(context.getFilesDir() + saveDir);
        mPIC_DIR = new File(mSAVE_DIR.toString() + "/pictures");
    }

    /**
     * Loads all the exercises
     * @return a {@link List} of {@link SelectableExercise} which contain all the Exercises saved in the app's directory
     *  or null if an error occurred
     */
    public List<SelectableExercise> loadAllExercises(){
        String [] previews = mSAVE_DIR.list();
        ArrayList<SelectableExercise> toReturn = new ArrayList<>();
       /* for(int i = 0; i < previews.length; i++){
            //Load the JSON String from the file
            String jsonFormat = loadFileInJSON(previews[i]);
            //return null
            if(jsonFormat == null)
                return null;
            //convert the saved file into the proper selectable exercise
            SelectableExercise toAdd;
            toReturn.add(toAdd)
        }*/
        return toReturn;
    }

    /**
     * Loads the file as a JSON string
     * @param name the name of the file to load
     * @return A {@link String} in JSON format which represents an exercise
     */
    private String loadFileInJSON(String name){
        String toLoad = mSAVE_DIR + name + ".wrk";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(toLoad);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves an exercise into memory by converting it into a JSON string and serializing it
     * @param exercise The exercise to save into the file system
     * @return True if the exercise was saved into the file system
     */
    public boolean saveExercise(SelectableExercise exercise){
        return false;
    }


}
