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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.NavGraphDirections;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.adapters.CategoriesAdapter;
import es.iesnervion.mcasado.todolists.interfaces.TitleChanger;
import es.iesnervion.mcasado.todolists.viewmodels.MainViewModel;


/**
 * Fragment used to show the list of categories
 */
public class CategoriesListFragment extends Fragment {

    private RecyclerView recycler;
    private MainViewModel viewModel;

    public CategoriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We'll share ViewModel with Activity because we will manage the same stuff there (list of
        //categories mainly)
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                ((CategoriesAdapter)CategoriesListFragment.this.recycler.getAdapter())
                                                                        .setCategories(categories);
                CategoriesListFragment.this.recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_list, container, false);
        FloatingActionButton btnAddTask = v.findViewById(R.id.addCategory);
        btnAddTask.setOnClickListener(view -> {
            NavDirections action = NavGraphDirections.actionGlobalAddEditCategoryFragment();
            Navigation.findNavController(view).navigate(action);
        });

        if (getArguments()!=null){
            boolean isCategoryAdded = CategoriesListFragmentArgs.fromBundle(getArguments()).
                                                                        getAddedCategory();
            if (isCategoryAdded) {
                Snackbar.make(btnAddTask,R.string.insertion_cat_ok, Snackbar.LENGTH_SHORT).show();
            }
        }


        recycler = v.findViewById(R.id.catRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(),
                                            RecyclerView.VERTICAL,false));
        CategoriesAdapter adapter = new CategoriesAdapter(requireActivity(),
                                                          viewModel.getAllCategories().getValue());
        recycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                          @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getResources().getString(R.string.manage_categories);
        ((TitleChanger)requireActivity()).changeToolBarTitle(title);
    }
}