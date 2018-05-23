package backend;

public abstract class Exercise {
    //The name of the exercise eg. Pushup, Pullups, Situps
    protected String mName;

    public Exercise(String name){
        mName = name;
    }
    public abstract String toString();
}
