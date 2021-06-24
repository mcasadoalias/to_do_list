package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithTasks {
    @Embedded
    private Category category;
    @Relation (
            parentColumn = "id",
            entityColumn = "catId",
            entity = Task.class
    )
    private List<Task> tasks;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
