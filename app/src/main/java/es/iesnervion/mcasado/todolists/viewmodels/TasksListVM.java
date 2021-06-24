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
    private MutableLiveData<WhatToShow> whatToShowMutable;
    private LiveData<List<Task>> tasks;

    public TasksListVM(@NonNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
        this.app = application;
        //TODO repo could be injected (Dagger or Hilt)
        this.repo = new Repository(TodoDB.getTodoDB(app).categoryDAO(),
                                    TodoDB.getTodoDB(app).taskDAO());
        this.whatToShowMutable = new MutableLiveData<>();
        //TODO WhatToShow HARDCODED: REMOVE!!!
        this.whatToShowMutable.setValue(new WhatToShow(WhatToShowType.CAT,7));
        this.tasks = getTasksByWhatToShow (this.whatToShowMutable);
    }

    private LiveData<List<Task>> getTasksByWhatToShow(
                            @NonNull @NotNull MutableLiveData<WhatToShow> whatToShowMutable) {
        LiveData<List<Task>> tasks = null;
        switch (whatToShowMutable.getValue().getWhatToShowType()) {
            case ALL:

                break;
            case FAV:
                break;
            case HIGH:
            case LOW:
                break;
            case CAT:
                tasks = Transformations.switchMap(whatToShowMutable, param ->
                        repo.tasksByCat(param.getCatId()));
                break;
        }
        return tasks;
    }

    public MutableLiveData<WhatToShow> getWhatToShowMutable() {
        return whatToShowMutable;
    }

    public void setWhatToShowMutable(WhatToShow whatToShow) {
        this.whatToShowMutable.setValue(whatToShow);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    //TODO: Remove this. Just testing.
    public void insertTask() {
        String title = "PRUE";
        String descr = "a ver...";
        Priority pri = Priority.HIGH;
        //TODO If date field is empty, the app crashes
        LocalDate date = Converters.LocalDatefromLong(1262165521l);
        LocalTime time = LocalTime.parse("12:30");
        //TODO: FOR NOW ALL TASKS ARE INSERTED IN LIST ID 1: CHANGE IT!!
        Task task = new Task(title, descr, pri, date, time, 1);
        //TODO Move this to a Repository
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            TodoDB.getTodoDB(app).taskDAO().insertTask(task);
            /*handler.post(() -> {
                //UI Thread work here
            });*/
        });
    }



}
