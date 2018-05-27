package vlad.DAO;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import vlad.backend.Workout;
import vlad.backend.WorkoutPreview;

/**
 * A Data access object for workouts saved on the device
 * Functionality includes loading and saving workouts as well
 *  as workout previews
 * Files saved as in WORKOUT_SAVE_DIRECTORY
 *  as name.wrk
 */
public class WorkoutDAO {
    //Directory for where workouts are saved
    private final static String WORKOUT_SAVE_DIRECTORY = "/workouts";
    //Directory for the picture save location
    private final static String WORKOUT_PICTURE_DIRECTORY = "/workouts/pictures";
    /** TODO Test
     * Loads all the workouts as a preview
     * @param context  The context of the activity or fragment calling this method
     * @return a list of workouts
     */
    public static WorkoutPreview[] loadAllWorkoutPreviews(Context context){
        File workoutDir = new File(context.getFilesDir().toString());
        String [] workouts = workoutDir.list();
        List<WorkoutPreview>  workoutPreviews= new LinkedList<>();
        for(int i = 0; i < workouts.length; i++){
            workoutPreviews.add(new WorkoutPreview(workouts[i].
                    substring(0,workouts[i].length() - 4)));
        }
        return workoutPreviews.toArray(new WorkoutPreview[0]);
    }

    /** TODO test
     * loads a workout object from the device
     * @throws FileNotFoundException if the file was not found
     * @param name the name of which workout to load
     * @return the selected workout exercise
     */
    public static Workout loadWorkout(String name, Context context) throws IOException, ClassNotFoundException {
        File toLoad = new File(context.getFilesDir().toString() + WORKOUT_SAVE_DIRECTORY + name + ".wrk");
        FileInputStream fis = new FileInputStream(toLoad);
        ObjectInputStream ois = ois = new ObjectInputStream(fis);
        return (Workout) ois.readObject();
    }

    /** TODO test
     * Saves a workout to the device
     * @param workout the workout to be saved
     * @param context The context of the activity or fragment calling this method
     * @return whether the saving was successful
     */
    public static boolean saveWorkout(Workout workout, Context context){
        File saveFile = new File(context.getFilesDir().toString() + WORKOUT_SAVE_DIRECTORY + workout.getName() + ".wrk");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFile);
        } catch (FileNotFoundException e) {
            return false;
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            return false;
        }
        try {
            oos.writeObject(workout);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /** TODO test
     * Setup the directory for the DAO
     * Should only be run if the directories are not set up
     * @return whether it successfully created the directories
     */
    public static boolean initializeDirectory(){
        boolean workoutDirSuccess = new File(WORKOUT_SAVE_DIRECTORY).mkdir();
        boolean pictureDirSuccess = new File(WORKOUT_PICTURE_DIRECTORY).mkdir();
        return workoutDirSuccess && pictureDirSuccess;
    }
}
