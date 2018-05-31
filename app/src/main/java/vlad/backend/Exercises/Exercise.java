package vlad.backend.Exercises;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public class Exercise implements Serializable{

    //The name of the exercise eg. Pushup, Pullups, Situps
    private String mName;

    public Exercise(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }
}
