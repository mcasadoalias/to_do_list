package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface TaskDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertTask (Task task);

}
