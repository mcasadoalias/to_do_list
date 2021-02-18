package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Task {

    //TODO Maybe it's better to store date and time separately. It depends on how you set reminders
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime dateTime;

    public Task(String title, String description, Priority priority, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
