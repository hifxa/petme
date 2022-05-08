package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.petme.app.R;
import com.petme.app.databinding.ActivityDashBinding;
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
                showHideBar(false);
                break;
            case R.id.profile:
                showHideBar(false);
                break;
            case R.id.vetFragment:
                showHideBar(true);
                break;
            case R.id.taskFragment:
                showHideBar(true);
                break;
            default:

                break;
        }
    }

    private void showHideBar(boolean state){
        if (state){
            bind.navBar.animate().translationY(bind.navBar.getHeight()).setDuration(200);
            bind.topBar.animate().translationY(-(bind.topBar.getHeight())).setDuration(200);
        }else{
            bind.navBar.animate().translationY(0).setDuration(200);
            bind.topBar.animate().translationY(0).setDuration(200);
        }
    }
}