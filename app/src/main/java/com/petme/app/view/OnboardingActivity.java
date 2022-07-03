package com.petme.app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.petme.app.R;
import com.petme.app.controllers.OnboardAdapter;
import com.petme.app.databinding.ActivityOnboardingBinding;
import com.petme.app.view.auth.AuthActivity;
import com.xcode.onboarding.OnBoardingPage;

import java.util.ArrayList;
import java.util.List;


@SuppressLint ( "SetTextI18n" )
public class OnboardingActivity extends AppCompatActivity {

	List < OnBoardingPage > pages = new ArrayList <> ( );
	TextView[] dots;
	private ActivityOnboardingBinding bind;
	private final ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback ( ) {
		@Override
		public void onPageSelected ( int position ) {

			if ( position != 0 ) {
				dots[ position - 1 ].setTextColor ( ContextCompat.getColor ( OnboardingActivity.this , R.color.black_50 ) );
			}

			if ( position < pages.size ( ) - 1 ) {
				dots[ position + 1 ].setTextColor ( ContextCompat.getColor ( OnboardingActivity.this , R.color.black_50 ) );
			}

			if ( position == pages.size ( ) - 1 ) {
				bind.next.setText ( "Finish" );
			}
			else {
				bind.next.setText ( "Next" );
			}

			dots[ position ].setTextColor ( ContextCompat.getColor ( OnboardingActivity.this , R.color.primary ) );
		}
	};

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );

//		WindowCompat.setDecorFitsSystemWindows ( getWindow ( ) , false );

		getWindow ( ).clearFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
		getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
		getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );

		getWindow ( ).getDecorView ( ).setSystemUiVisibility ( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE );

		getWindow ( ).setStatusBarColor ( Color.TRANSPARENT );
		getWindow ( ).setNavigationBarColor ( Color.TRANSPARENT );

		bind = ActivityOnboardingBinding.inflate ( getLayoutInflater ( ) );
		setContentView ( bind.getRoot ( ) );

		OnboardAdapter adapter = new OnboardAdapter ( pages );

		pages.add ( new OnBoardingPage ( R.drawable.splash_illus , R.color.primaryContainer , R.color.onPrimaryContainer ,
				"PETME" , "Improve your work-life balance without compromising on the care your pet needs!" ) );

		pages.add ( new OnBoardingPage ( R.drawable.shop_onboarding , R.color.shopContainer , R.color.onShop ,
				"NEARBY PET SHOPS" , "Nearby vets and pet shops are now just a click away" ) );

		pages.add ( new OnBoardingPage ( R.drawable.task_onboading , R.color.taskContainer , R.color.onTaskContainer ,
				"TASK LIST" , "Create your own pet to-do list." ) );

		pages.add ( new OnBoardingPage ( R.drawable.adoptonboading , R.color.adoptContainer , R.color.onAdoptContainer ,
				"ADOPT PETS" , "Put up pets for adoption or connect with the ones you want to adopt." ) );

		pages.add ( new OnBoardingPage ( R.drawable.lost_and_found_onboarding , R.color.lostFoundContainer , R.color.onLostFoundContainer ,
				"LOST AND FOUND" , "Posting lost or found ads has never been easier." ) );

		dots = new TextView[ pages.size ( ) ];

		for ( int i = 0 ; i < dots.length ; i++ ) {
			dots[ i ] = new TextView ( this );
			dots[ i ].setText ( "•" );
			dots[ i ].setTextSize ( 50 );
			dots[ i ].setTextColor ( ContextCompat.getColor ( OnboardingActivity.this , R.color.black_50 ) );
			bind.indicator.addView ( dots[ i ] );
		}

		bind.viewpager.setAdapter ( adapter );
		bind.viewpager.registerOnPageChangeCallback ( callback );

		bind.next.setOnClickListener ( v -> {
			int currentPage = bind.viewpager.getCurrentItem ( );

			if ( currentPage == pages.size ( ) - 1 ) {
				startActivity ( new Intent ( this , AuthActivity.class ) );
				finishAfterTransition ( );
			}
			else {
				bind.viewpager.setCurrentItem ( currentPage + 1 );
			}
		} );

	}
}