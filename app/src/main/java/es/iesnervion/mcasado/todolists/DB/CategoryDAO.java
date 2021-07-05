package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Transaction
    @Query("SELECT * FROM Category")
    public List<CategoryWithTasks> getCategoriesWithTasks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory (Category category);
}
