package es.iesnervion.mcasado.todolists.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.NavGraphDirections;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;
import es.iesnervion.mcasado.todolists.fragments.AddEditTaskFragmentDirections;
import es.iesnervion.mcasado.todolists.interfaces.MenuChanger;
import es.iesnervion.mcasado.todolists.interfaces.TitleChanger;
import es.iesnervion.mcasado.todolists.viewmodels.AddEditTaskVM;
import es.iesnervion.mcasado.todolists.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements TitleChanger, MenuChanger {

    MainViewModel viewModel;
    private DrawerLayout drawer;
    MaterialToolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                                                        .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // DrawerNavigation & toolbar
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigationView);
        toolbar.setNavigationOnClickListener(v -> drawer.open());
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            NavDirections action = null;
            switch (item.getItemId()){
                case R.id.manage_categories:
                    action = NavGraphDirections.actionGlobalCategoriesListFragment(false);
                    break;
                case R.id.allTasks:
                    action = NavGraphDirections.actionGlobalTasksListFragment(
                                    false, new WhatToShow(WhatToShowType.ALL,
                                                                    WhatToShow.NO_CAT));
                    break;
                case R.id.favTasks:
                    action = NavGraphDirections.actionGlobalTasksListFragment(
                            false, new WhatToShow(WhatToShowType.FAV,
                                    WhatToShow.NO_CAT));
                    break;
                case R.id.highPriorityTasks:
                    action = NavGraphDirections.actionGlobalTasksListFragment(
                            false, new WhatToShow(WhatToShowType.HIGH,
                                    WhatToShow.NO_CAT));
                    break;
                case R.id.lowPriorityTasks:
                    action = NavGraphDirections.actionGlobalTasksListFragment(
                            false, new WhatToShow(WhatToShowType.LOW,
                                    WhatToShow.NO_CAT));
                    break;
                default:
                    action = NavGraphDirections.actionGlobalTasksListFragment(false,
                               new WhatToShow(WhatToShowType.CAT,item.getItemId()));
            }

            navController.navigate(action);
            drawer.close();
            return true;
        });

        //Read all categories from DB and create a menu item for each of them
        this.viewModel.getAllCategories().observe(this, categories -> {

            Menu menu = navigationView.getMenu();
            menu.removeGroup(R.id.gr_categories);
            int i=1;
            for (Category cat : categories){
                menu.add(R.id.gr_categories,cat.getId(),i, cat.getTitle());
                i++;
            }
        });

    }

    @Override
    public void changeToolBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    //TODO Probably not needed anymore
    @Override
    public void addMenuItem(int groupId, int itemId, int order, String title) {

        navigationView.getMenu().add(groupId,itemId,order,title);
    }
}