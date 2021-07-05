package es.iesnervion.mcasado.todolists.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.DB.Repository;
import es.iesnervion.mcasado.todolists.DB.TodoDB;

public class MainViewModel extends AndroidViewModel {
    private final Repository repo;
    private final Application app;

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.app = application;
        //TODO repo could be injected (Dagger or Hilt)
        this.repo = new Repository(TodoDB.getTodoDB(app).categoryDAO(),
                TodoDB.getTodoDB(app).taskDAO());
    }

    public LiveData<List<Category>> getAllCategories (){
        return this.repo.getAllCategories();
    }
}
