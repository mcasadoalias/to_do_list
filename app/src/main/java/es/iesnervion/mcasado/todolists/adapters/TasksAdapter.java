package es.iesnervion.mcasado.todolists.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.R;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    List<Task> tasks;

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTasks (List<Task> tasks){
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
            holder.getCbTitle().setText(tasks.get(position).getTitle());
            holder.getTxvDescrip().setText(tasks.get(position).getDescription());
            LocalDate date = tasks.get(position).getDate();
            DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            holder.getTxvDate().setText(date.format(format));

            holder.getTxvTime().setText(tasks.get(position).getTime().toString());
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

        private final CheckBox cbTitle;
        private final TextView txvDescrip;
        private final TextView txvDate;
        private final TextView txvTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            // TODO: Click Listener
            this.cbTitle = itemView.findViewById(R.id.cbTaskTitle);
            this.txvDescrip = itemView.findViewById(R.id.txvDescrip);
            this.txvDate = itemView.findViewById(R.id.txvDate);
            this.txvTime = itemView.findViewById(R.id.txvTime);

        }

        public CheckBox getCbTitle() {
            return cbTitle;
        }

        public TextView getTxvDescrip() {
            return txvDescrip;
        }

        public TextView getTxvDate() {
            return txvDate;
        }

        public TextView getTxvTime() {
            return txvTime;
        }
    }
}
