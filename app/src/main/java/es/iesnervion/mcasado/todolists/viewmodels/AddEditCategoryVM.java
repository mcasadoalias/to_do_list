package es.iesnervion.mcasado.todolists.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;
import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.DB.Repository;
import es.iesnervion.mcasado.todolists.DB.TodoDB;
import es.iesnervion.mcasado.todolists.WhatToShow;

public class AddEditCategoryVM extends AndroidViewModel {
    private static final String CATEGORY_TITLE_KEY = "CategoryTitleKey";
    private final Repository repo;
    private final SavedStateHandle state;
    private final Application app;
    private LiveData<Long> idCat;

    public AddEditCategoryVM(@NonNull @NotNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
        this.app = application;
        //TODO repo could be injected (Dagger or Hilt)
        this.repo = new Repository(TodoDB.getTodoDB(app).categoryDAO(),
                                   TodoDB.getTodoDB(app).taskDAO());
        this.idCat = this.repo.getIdCat();
    }

    public void saveTitle(String title) {
        state.set(CATEGORY_TITLE_KEY, title);
    }

    public String getTitle() {
        return state.get(CATEGORY_TITLE_KEY);
    }

    public void insertCategory() {
        Category category = new Category(getTitle());
        repo.insertCategory(category);
    }

    public LiveData<Long> getIdCat() {
        return idCat;
    }
}
