package es.iesnervion.mcasado.todolists.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.DB.TodoDB;

public class AddEditCategoryVM extends AndroidViewModel {
    private static final String CATEGORY_TITLE_KEY = "CategoryTitleKey";
    ExecutorService executor = Executors.newSingleThreadExecutor();
    private final SavedStateHandle state;
    private final Application app;

    public AddEditCategoryVM(@NonNull @NotNull Application application, SavedStateHandle state) {
        super(application);
        this.state = state;
        this.app = application;

    }

    public void saveTitle(String title) {
        state.set(CATEGORY_TITLE_KEY, title);
    }

    public String getTitle() {
        return state.get(CATEGORY_TITLE_KEY);
    }

    public void insertCategory() {
        String title = getTitle();

        Category category = new Category(title);
        //TODO Move this to a Repository
        executor.execute(() -> {
            TodoDB.getTodoDB(app).categoryDAO().insertCategory(new Category(title));
            /*handler.post(() -> {
                //UI Thread work here
            });*/
        });
    }
}
