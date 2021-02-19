package es.iesnervion.mcasado.todolists.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate date;
    private LocalTime time;

    public Task(String title, String description, Priority priority, LocalDate date, LocalTime time)
    {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.time = time;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
