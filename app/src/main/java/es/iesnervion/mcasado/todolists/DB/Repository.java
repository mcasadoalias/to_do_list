package es.iesnervion.mcasado.todolists.DB;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;

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

    public LiveData<List<Task>> allTasks() {
        return taskDAO.getAllTasks();
    }



    public LiveData<List<Task>> getTasksByWhatToShow(WhatToShowType whatToShowType, int catId) {
        LiveData<List<Task>> tasks = null;
        switch (whatToShowType) {
            case ALL:
                tasks = allTasks();
                break;
            case FAV:
                break;
            case HIGH:
            case LOW:
                break;
            case CAT:
                tasks = tasksByCat(catId);
                break;
        }
        return tasks;
    }
}
