package vlad.DAO;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import vlad.Previews.PreviewFactory;

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
    private PreviewFactory<Preview> factory;

    public GeneralDAO(Context context, String saveDir){
        mSAVE_DIR = new File(context.getFilesDir() + saveDir);
        mPIC_DIR = new File(mSAVE_DIR.toString() + "/pictures");
    }

    /** TODO test
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
            toReturn.add(factory.createT(previews[i]));
        }
        return toReturn;
    }

    /** TODO test
     * loads a backend object from the device
     * @throws FileNotFoundException if the file was not found
     * @param name the name of which workout to load
     * @return the selected workout exercise
     */
    public Backend loadBackend(String name) throws IOException, ClassNotFoundException {
        String toLoad = mSAVE_DIR + name + ".wrk";
        FileInputStream fis = new FileInputStream(toLoad);
        ObjectInputStream ois = ois = new ObjectInputStream(fis);
        return (Backend) ois.readObject();
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

}
