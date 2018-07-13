package vlad.backend.Exercises;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public class Exercise implements Serializable, Comparable{
    private static final long serialVersionUID = 1L;

    //The name of the exercise eg. Pushup, Pullups, Situps
    private String mName;

    public Exercise(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    @Override
    public String toString(){
        return this.mName;
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

}
