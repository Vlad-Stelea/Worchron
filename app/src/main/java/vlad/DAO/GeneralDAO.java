package vlad.DAO;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import vlad.Previews.PreviewFactory;
import vlad.backend.Algorithms.Sorters;
import vlad.backend.Exercises.SelectableExercise;

/**
 * Generalized Data Access Object for this project which is responsible for persistance of various classes
 * @param <Backend> The backend class which will be in persisted eg. Workout or exercise
 * @param <Preview> The backend of a summarized class eg WorkoutPreview
 *                 Does not include the view
 */
public class GeneralDAO<Backend, Preview> {
    //Directory where Backend will be saved
    private final File mSAVE_DIR;
    //Directory where the pictures for Backend will be saved
    private final File mPIC_DIR;
    //The factory which will create the preview objects
    private PreviewFactory<Preview> mFactory;

    public GeneralDAO(Context context, String saveDir, PreviewFactory factory){
        mSAVE_DIR = new File(context.getFilesDir() + saveDir);
        mPIC_DIR = new File(mSAVE_DIR.toString() + "/pictures");
        mFactory = factory;
    }

    /**
     * Loads previews of all the items saved
     *  Does this by looking into the directory that was passed in and listing all the files
     * @return A list of previews
     */
    public List<Preview> loadAllPreviews(){
        String[] previews = mSAVE_DIR.list();
        List<Preview> toReturn = new ArrayList<>();
        //Start at one to skip pictures.
        //TODO test if directories are at top. If not find other way to avoid pictures directory
        for(int i = 1; i < previews.length; i++){
            toReturn.add(mFactory.createT(previews[i].
                    substring(0,
                            previews[i].length() -4)
            ));
        }
        alphabetize((List<? extends Comparable>) toReturn);
        return toReturn;
    }

    /** TODO test
     * loads a backend object from the device
     * @throws FileNotFoundException if the file was not found
     * @param name the name of which workout to load
     * @return the selected workout exercise
     */
    public Backend loadBackend(String name) throws IOException, ClassNotFoundException {
        String toLoad = mSAVE_DIR + "/" + name;
        FileInputStream fis = new FileInputStream(toLoad);
        ObjectInputStream ois = ois = new ObjectInputStream(fis);
        return (Backend) ois.readObject();
    }

    public boolean saveBackend(Backend backend, Context context){
        File saveFile = new File( mSAVE_DIR + "/" + backend.toString() + ".wrk");
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
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
            oos.writeObject(backend);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Setup the directory for the DAO
     * Should only be run if the directories are not set up
     * @return whether it successfully created the directories
     */
    public boolean initializeDirectory(){
        boolean workoutDirSuccess = mSAVE_DIR.mkdir();
        boolean pictureDirSuccess = mPIC_DIR.mkdir();
        return workoutDirSuccess && pictureDirSuccess;
    }

    public List<Backend> loadAllBackend(){
        String[] previews = mSAVE_DIR.list();
        List<Backend> toReturn = new ArrayList<>();
        for(int i = 1; i < previews.length; i++){
            try {
                toReturn.add(loadBackend(previews[i]));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return toReturn;
    }

    private void alphabetize(List<? extends Comparable> toAlphabetize){
        Sorters.quickSort((List<Comparable>) toAlphabetize);
    }


}
