package vlad.backend;

public class WorkoutPreview {

    private String mName;
    public WorkoutPreview(String name){
        mName = name;
    }

    /**
     * Gets the name of the workout
     * @return the name of the workout
     */
    public String getName(){
        return mName;
    }
}
