package es.iesnervion.mcasado.todolists.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface TaskDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertTask (Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks ();

    @Query("SELECT * FROM task WHERE catId=:id")
    LiveData<List<Task>> getTasksByCat (int id);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateTask (Task task);

    @Query("SELECT * FROM task WHERE priority=:priority")
    LiveData<List<Task>> getTasksByPriority(Priority priority);

    @Query("SELECT * FROM task WHERE fav=:fav")
    LiveData<List<Task>> getTasksByFav(boolean fav);
}
