package es.iesnervion.mcasado.todolists.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.iesnervion.mcasado.todolists.DB.Converters;
import es.iesnervion.mcasado.todolists.DB.Priority;
import es.iesnervion.mcasado.todolists.DB.Repository;
import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.DB.TodoDB;
import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;

/**
 * ViewModel for TasksListFragment
 * We will not use a single ViewModel shared with all fragments and the activity because we will
 * apply the principle of separation of concerns.
 *
 */
public class TasksListVM extends AndroidViewModel {
    private final SavedStateHandle state;
    private final Application app;
    private final Repository repo;
    private static final String WHATTOSHOW_KEY = "WhatToShowKey";
    private LiveData<List<Task>> tasks;

    public TasksListVM(@NonNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
        this.app = application;
        //TODO repo could be injected (Dagger or Hilt)
        this.repo = new Repository(TodoDB.getTodoDB(app).categoryDAO(),
                                    TodoDB.getTodoDB(app).taskDAO());

        this.state.set(WHATTOSHOW_KEY,new WhatToShow());

        this.tasks = Transformations.switchMap(this.state.getLiveData(WHATTOSHOW_KEY), param ->
                               repo.getTasksByWhatToShow (((WhatToShow)param).getWhatToShowType(),
                                                          ((WhatToShow)param).getCatId()));
    }

    public void saveWhatToShow (WhatToShow whatToShow){
        this.state.set(WHATTOSHOW_KEY,whatToShow);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void updateTask (Task task){
        repo.updateTask(task);
    }

}
