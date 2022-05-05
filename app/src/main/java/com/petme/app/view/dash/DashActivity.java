package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.petme.R;
import com.example.petme.databinding.ActivityDashBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    ActivityDashBinding bind;
    NavHostFragment navView;
    NavController ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = ActivityDashBinding.inflate ( getLayoutInflater () );
        setContentView(bind.getRoot ());

        navView = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_view);
        ctrl = navView.getNavController();

        NavigationUI.setupWithNavController(bind.navBar, ctrl);
        ctrl.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
        switch (navDestination.getId()) {
            case R.id.home:

                break;
            case R.id.profile:

                break;
            default:

                break;
        }
    }
}