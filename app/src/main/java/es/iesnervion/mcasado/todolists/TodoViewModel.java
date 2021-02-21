package es.iesnervion.mcasado.todolists;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;

public class TodoViewModel extends AndroidViewModel {
    private static final String DUE_DATE_KEY = "dueDateKey";
    private SavedStateHandle state;
    public TodoViewModel(@NonNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
    }

    public void saveDueDate (String date){
        state.set(DUE_DATE_KEY,date);
    }

    public String getDueDate (){
        return state.get(DUE_DATE_KEY);
    }
}
