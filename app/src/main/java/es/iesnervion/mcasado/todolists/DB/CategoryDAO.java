package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Transaction
    @Query("SELECT * FROM Category")
    public List<CategoryWithTasks> getCategoriesWithTasks();
}
