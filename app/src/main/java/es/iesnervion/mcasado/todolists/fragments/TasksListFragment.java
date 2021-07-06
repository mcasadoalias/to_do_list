package es.iesnervion.mcasado.todolists.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;
import es.iesnervion.mcasado.todolists.adapters.TasksAdapter;
import es.iesnervion.mcasado.todolists.interfaces.TitleChanger;
import es.iesnervion.mcasado.todolists.viewmodels.TasksListVM;

/**
 * Fragment used to show the list of tasks
 */
public class TasksListFragment extends Fragment {

    private RecyclerView recycler;
    private TasksListVM viewModel;

    public TasksListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TasksListVM.class);
        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                       ((TasksAdapter)TasksListFragment.this.recycler.getAdapter()).setTasks(tasks);
                       TasksListFragment.this.recycler.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        FloatingActionButton btnAddTask = v.findViewById(R.id.addTask);
        btnAddTask.setOnClickListener(view -> {
            NavDirections action = TasksListFragmentDirections
                                    .actionTasksListFragmentToAddEditTaskFragment();
            Navigation.findNavController(view).navigate(action);
        });
        if (getArguments()!=null){
            boolean isTaskAdded = TasksListFragmentArgs.fromBundle(getArguments()).getAddedTask();
            if (isTaskAdded) {
                Snackbar.make(btnAddTask,R.string.insertion_ok, Snackbar.LENGTH_SHORT).show();
            }

            WhatToShow whatToShow = TasksListFragmentArgs.fromBundle(getArguments())
                                                                            .getWhatToShow();
            if (whatToShow==null){
                viewModel.saveWhatToShow(new WhatToShow());
            } else {
                viewModel.saveWhatToShow(whatToShow);
            }

        }


        recycler = v.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(),
                                                        RecyclerView.VERTICAL,false));
        TasksAdapter adapter = new TasksAdapter(requireContext(), viewModel.getTasks().getValue());
        recycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                          @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title="";
        WhatToShow whatToShow = viewModel.getWhatToShow();
        switch (whatToShow.getWhatToShowType()){
            case ALL:
                title = getResources().getString(R.string.all_tasks);
                break;
            case FAV:
                title = getResources().getString(R.string.favourite_tasks);
                break;
            case HIGH:
                title = getResources().getString(R.string.high_priority_tasks);
                break;
            case LOW:
                title = getResources().getString(R.string.low_priority_tasks);
                break;
            case CAT:
                //TODO: Get category title!
                title = "Some category... ";
        }
        ((TitleChanger)requireActivity()).changeToolBarTitle(title);
    }
}