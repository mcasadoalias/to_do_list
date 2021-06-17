package es.iesnervion.mcasado.todolists;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.iesnervion.mcasado.todolists.DB.Converters;
import es.iesnervion.mcasado.todolists.DB.Priority;
import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.DB.TodoDB;

public class TodoViewModel extends AndroidViewModel {
    private static final String DUE_DATE_KEY = "dueDateKey";
    private static final String DUE_DATE_STRING_KEY = "dueDateStringKey";
    private static final String DUE_TIME_KEY = "dueTimeKey";
    private static final String TASK_TITLE_KEY = "taskTitleKey";
    private static final String TASK_DESCRIPTION_KEY = "TaskDescriptionKey";
    private static final String TASK_PRIORITY_KEY = "TaskPriorityKey";
    ExecutorService executor = Executors.newSingleThreadExecutor();
    //Handler handler = new Handler(Looper.getMainLooper());
    private final SavedStateHandle state;
    private final Application app;

    public TodoViewModel(@NonNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
        this.app = application;
    }

    //TODO: Change all the "get" for "getLiveData"?

    public void saveDueDate (Long date){
        state.set(DUE_DATE_KEY,date);
    }

    public void saveDueDateAsString(String strDate) {state.set(DUE_DATE_STRING_KEY,strDate); }

    public Long getDueDate (){
        return state.get(DUE_DATE_KEY);
    }

    public String getDueDateAsString (){
        return state.get(DUE_DATE_STRING_KEY);
    }

    public void saveDueTime (String time){
        state.set(DUE_TIME_KEY,time);
    }

    public String getDueTime (){
        return state.get(DUE_TIME_KEY);
    }

    public void saveTitle (String title){
        state.set(TASK_TITLE_KEY,title);
    }

    public String getTitle (){
        return state.get(TASK_TITLE_KEY);
    }



    public void saveDescription(String description) {
        state.set(TASK_DESCRIPTION_KEY, description);
    }

    public String getDescription (){
        return state.get(TASK_DESCRIPTION_KEY);
    }

    public void savePriority(Priority priority) {
        state.set(TASK_PRIORITY_KEY, priority);
    }

    public Priority getPriority() {
        return state.get(TASK_PRIORITY_KEY);
    }

    public void clearTaskData() {
        saveTitle("");
        saveDescription("");
        savePriority(Priority.UNDEFINED);
        saveDueDate(null);
        saveDueTime("");
    }

    public void insertTask() {
        String title = getTitle();
        String descr = getDescription();
        Priority pri = getPriority();
        LocalDate date = Converters.LocalDatefromLong(getDueDate());
        LocalTime time = LocalTime.parse(getDueTime());
        Task task = new Task(title, descr, pri , date, time);
        //TODO Move this to a Repository
        executor.execute(() -> {
            TodoDB.getTodoDB(app).taskDAO().insertTask(task);
            /*handler.post(() -> {
                //UI Thread work here
            });*/
        });

    }

}
