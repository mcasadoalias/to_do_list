package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface TaskDAO {

    @Insert
    void insertTask (Task task);

}
