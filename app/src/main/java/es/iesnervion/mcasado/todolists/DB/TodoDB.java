package es.iesnervion.mcasado.todolists.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities={Task.class, Category.class}, version=1)
@TypeConverters({Converters.class})
public abstract class TodoDB extends RoomDatabase {

    private static TodoDB INSTANCE;

    public abstract TaskDAO taskDAO();
    public abstract CategoryDAO categoryDAO();

    public static TodoDB getTodoDB (final Context context){
        if (INSTANCE == null) {
            synchronized (TodoDB.class) {
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    TodoDB.class, "todolists.db")
                                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
