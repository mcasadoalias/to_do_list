package es.iesnervion.mcasado.todolists.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.viewmodels.TasksListVM;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> tasks;
    private Context context;
    private TasksListVM viewModel;

    public TasksAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(TasksListVM.class);
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
            Task task = tasks.get(position);
            //Checkbox (completed task)
            holder.getCbTitle().setText(task.getTitle());
            holder.getCbTitle().setChecked(task.isCompleted());
            holder.getCbTitle().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setCompleted(isChecked);
                    viewModel.updateTask(task);
                    if (isChecked) {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() |
                                                    Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.getTxvDescrip().setPaintFlags(buttonView.getPaintFlags() |
                                                                Paint.STRIKE_THRU_TEXT_FLAG);
                        // TODO Retrieve colours from theme
                        holder.getCard().setCardBackgroundColor(Color.parseColor("#e9ecf2"));
                    } else {
                        buttonView.setPaintFlags(buttonView.getPaintFlags() &
                                                    (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        holder.getTxvDescrip().setPaintFlags(buttonView.getPaintFlags() &
                                                                (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        // TODO Retrieve colours from theme
                        holder.getCard().setCardBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }
            });

            //Description
            holder.getTxvDescrip().setText(task.getDescription());

            //Date
            LocalDate date = task.getDate();
            DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            holder.getTxvDate().setText(date.format(format));

            //Time
            holder.getTxvTime().setText(task.getTime().toString());

            //Fav button
            holder.getTgFav().setChecked(task.isFav());
            holder.getTgFav().setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            task.setFav(isChecked);
                            viewModel.updateTask(task);
                        }
                    });
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

        private final MaterialCardView card;
        private final CheckBox cbTitle;
        private final TextView txvDescrip;
        private final TextView txvDate;
        private final TextView txvTime;
        private final ToggleButton tgFav;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.card = itemView.findViewById(R.id.card);
            this.cbTitle = itemView.findViewById(R.id.cbTaskTitle);
            this.txvDescrip = itemView.findViewById(R.id.txvDescrip);
            this.txvDate = itemView.findViewById(R.id.txvDate);
            this.txvTime = itemView.findViewById(R.id.txvTime);
            this.tgFav = itemView.findViewById(R.id.tgFav);

        }

        public MaterialCardView getCard() {
            return card;
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

        public ToggleButton getTgFav() {
            return tgFav;
        }
    }
}
