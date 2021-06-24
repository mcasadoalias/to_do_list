package es.iesnervion.mcasado.todolists.DB;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {


    private final CategoryDAO catDAO;
    private final TaskDAO taskDAO;

    public Repository(CategoryDAO catDAO, TaskDAO taskDAO) {
        this.catDAO = catDAO;
        this.taskDAO = taskDAO;
    }


    public LiveData<List<Task>> tasksByCat(int id) {

        return taskDAO.getTasksByCat(id);
    }
}
