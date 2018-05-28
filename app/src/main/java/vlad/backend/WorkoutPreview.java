package vlad.backend;

import vlad.DAO.PreviewFactory;

/**
 * Represents a preview
 */
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

    public static class WorkoutPreviewFactory implements PreviewFactory<WorkoutPreview>{

        @Override
        public WorkoutPreview createT(String name) {
            return new WorkoutPreview(name);
        }
    }
}
