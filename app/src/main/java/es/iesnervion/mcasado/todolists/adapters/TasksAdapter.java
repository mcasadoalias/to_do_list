package es.iesnervion.mcasado.todolists.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.R;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    List<Task> tasks;

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TasksAdapter() {
        this.tasks = null;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasks_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (tasks!=null){
            holder.getTvTitle().setText(tasks.get(position).getTitle().toString());
        }
    }

    @Override
    public int getItemCount() {
        int size=0;
        if (tasks!=null) {
            size = tasks.size();
        }
        return size;
    }

    /**
     * ViewHolder Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvTitle;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            // TODO: Click Listener
            this.tvTitle = itemView.findViewById(R.id.tvtitle);
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
    }
}
