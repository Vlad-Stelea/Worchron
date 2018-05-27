package vlad.backend;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Exercises")
public class Exercise {
    @PrimaryKey
    public int uid;
    //The name of the exercise eg. Pushup, Pullups, Situps
    @ColumnInfo(name = "name")
    public String mName;

    public Exercise(String name){
        mName = name;
    }
}
