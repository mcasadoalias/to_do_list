package es.iesnervion.mcasado.todolists.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.viewmodels.AddEditTaskVM;

public class MainActivity extends AppCompatActivity {

    AddEditTaskVM viewModel;
    private DrawerLayout drawer;
    MaterialToolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
            drawer.close();
            return true;
        });

    }

}