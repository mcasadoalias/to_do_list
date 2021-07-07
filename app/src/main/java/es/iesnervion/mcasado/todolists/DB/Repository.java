package es.iesnervion.mcasado.todolists.DB;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;

public class Repository {


    private final CategoryDAO catDAO;
    private final TaskDAO taskDAO;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final LiveData<Long> idCatMut = new MutableLiveData<>();

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

    public LiveData<List<Task>> favTasks () {
        return taskDAO.getTasksByFav(true);
    }

    public LiveData<List<Task>> highPriorityTasks() {
        return taskDAO.getTasksByPriority(Priority.HIGH);
    }

    public LiveData<List<Task>> lowPriorityTasks() {
        return taskDAO.getTasksByPriority(Priority.LOW);
    }

    public LiveData<List<Task>> getTasksByWhatToShow(WhatToShowType whatToShowType, int catId) {
        LiveData<List<Task>> tasks = null;
        switch (whatToShowType) {
            case ALL:
                tasks = allTasks();
                break;
            case FAV:
                tasks = favTasks();
                break;
            case HIGH:
                tasks = highPriorityTasks();
                break;
            case LOW:
                tasks = lowPriorityTasks();
                break;
            case CAT:
                tasks = tasksByCat(catId);
                break;
        }
        return tasks;
    }

    public void updateTask (Task task){
        executor.execute(() -> {
            taskDAO.updateTask(task);
            /*handler.post(() -> {
                //UI Thread work here
            });*/
        });
    }

    public LiveData<List<Category>> getAllCategories (){
        return this.catDAO.getAllCategories();
    }

    public void insertCategory (Category category){
        executor.execute(() -> {
            long idCat = catDAO.insertCategory(category);
            ((MutableLiveData<Long>)idCatMut).postValue(idCat);
            /*handler.post(() -> {
                //UI Thread work here
            });*/
        });
    }

    public LiveData<Long> getIdCat () {
        return idCatMut;
    }

    public LiveData<String> getCategoryTitle(int catId) {
        return catDAO.getCategoryTitle(catId);
    }
}
