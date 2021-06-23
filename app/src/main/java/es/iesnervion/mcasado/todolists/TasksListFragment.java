package es.iesnervion.mcasado.todolists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksListFragment newInstance(String param1, String param2) {
        TasksListFragment fragment = new TasksListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                Snackbar.make(btnAddTask, R.string.insertion_ok, Snackbar.LENGTH_SHORT).show();
            }
        }

        return v;
    }
}