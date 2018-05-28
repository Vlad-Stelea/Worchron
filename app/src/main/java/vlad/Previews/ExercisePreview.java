package vlad.Previews;

public class ExercisePreview {
    private String mName;

    public ExercisePreview(String name){
        mName = name;
    }

    /**
     * Gets the name of the exercise this is previewing
     * @return name of the exercise preview
     */
    public String getName(){
        return mName;
    }

    public static class ExercisePreviewFactory implements PreviewFactory<ExercisePreview>{

        @Override
        public ExercisePreview createT(String name) {
            return new ExercisePreview(name);
        }
    }
}
