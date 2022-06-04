package com.petme.app.view.dash;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.petme.app.R;
import com.petme.app.databinding.ActivityDashBinding;
import com.petme.app.utils.Prefs;
import com.petme.app.view.auth.AuthActivity;

public class DashActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

	ActivityDashBinding bind;
	NavHostFragment navView;
	NavController ctrl;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );

		bind = ActivityDashBinding.inflate ( getLayoutInflater ( ) );
		setContentView ( bind.getRoot ( ) );

		navView = ( NavHostFragment ) getSupportFragmentManager ( ).findFragmentById ( R.id.nav_view );
		ctrl = navView.getNavController ( );

		NavigationUI.setupWithNavController ( bind.navBar , ctrl );
		ctrl.addOnDestinationChangedListener ( this );

		bind.topBar.setOnMenuItemClickListener ( item -> {
			switch ( item.getItemId ( ) ) {
				case R.id.about:
					break;

				case R.id.logout:
					logout ( );
					break;
			}
			return true;
		} );

	}

	private void logout ( ) {
		FirebaseAuth.getInstance ( ).signOut ( ); // this clear the local firebase auth session.
		new Prefs ( this ).nukeThemAll ( ); // this removes the locally saved user details.
		// this will redirect the user back to the auth screen and also andy previously existing activities
		startActivity ( new Intent ( this , AuthActivity.class ).setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK ) );
		finishAfterTransition ( );
	}

	@SuppressLint ( "NonConstantResourceId" )
	@Override
	public void onDestinationChanged ( @NonNull NavController navController , @NonNull NavDestination navDestination , @Nullable Bundle bundle ) {
		switch ( navDestination.getId ( ) ) {
			case R.id.home:
				showHideBar ( false );
				break;
			case R.id.profile:
				showHideBar ( false );
				break;
			case R.id.vetFragment:
				showHideBar ( true );
				break;
			case R.id.lostFoundFragment:
				showHideBar ( true );
				break;
			case R.id.taskFragment:
				showHideBar ( true );
				break;
			case R.id.shopsFragment:
				showHideBar ( true );
				break;
			case R.id.adoptFragment:
				showHideBar ( true );
				break;
			case R.id.matingFragment:
				showHideBar ( true );
				break;
			default:
				showHideBar ( true );
				break;
		}
	}

	private void showHideBar ( boolean state ) {
		if ( state ) {
			bind.navBar.animate ( ).translationY ( bind.navBar.getHeight ( ) ).setDuration ( 200 ).setListener ( new Animator.AnimatorListener ( ) {
				@Override
				public void onAnimationStart ( Animator animation ) {

				}

				@Override
				public void onAnimationEnd ( Animator animation ) {
					bind.navBar.setVisibility ( View.GONE );
				}

				@Override
				public void onAnimationCancel ( Animator animation ) {

				}

				@Override
				public void onAnimationRepeat ( Animator animation ) {

				}
			} );
			bind.topBar.animate ( ).translationY ( - ( bind.topBar.getHeight ( ) ) ).setDuration ( 200 ).setListener ( new Animator.AnimatorListener ( ) {
				@Override
				public void onAnimationStart ( Animator animation ) {

				}

				@Override
				public void onAnimationEnd ( Animator animation ) {
					bind.topBar.setVisibility ( View.GONE );
				}

				@Override
				public void onAnimationCancel ( Animator animation ) {

				}

				@Override
				public void onAnimationRepeat ( Animator animation ) {

				}
			} );
		}
		else {
			bind.navBar.animate ( ).translationY ( 0 ).setDuration ( 200 ).setListener ( new Animator.AnimatorListener ( ) {
				@Override
				public void onAnimationStart ( Animator animation ) {

				}

				@Override
				public void onAnimationEnd ( Animator animation ) {
					bind.navBar.setVisibility ( View.VISIBLE );
				}

				@Override
				public void onAnimationCancel ( Animator animation ) {

				}

				@Override
				public void onAnimationRepeat ( Animator animation ) {

				}
			} );
			bind.topBar.animate ( ).translationY ( 0 ).setDuration ( 200 ).setListener ( new Animator.AnimatorListener ( ) {
				@Override
				public void onAnimationStart ( Animator animation ) {

				}

				@Override
				public void onAnimationEnd ( Animator animation ) {
					bind.topBar.setVisibility ( View.VISIBLE );
				}

				@Override
				public void onAnimationCancel ( Animator animation ) {

				}

				@Override
				public void onAnimationRepeat ( Animator animation ) {

				}
			} );
		}
	}
}