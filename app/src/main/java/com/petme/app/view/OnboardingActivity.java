package com.petme.app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.petme.app.R;
import com.petme.app.view.auth.AuthActivity;
import com.xcode.onboarding.OnBoarder;
import com.xcode.onboarding.OnBoardingPage;
import com.xcode.onboarding.OnFinishLastPage;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<OnBoardingPage> pages = new ArrayList<>();
        pages.add(new OnBoardingPage(R.drawable.welcome_image,"NEARBY PET SHOPS","Nearby vets and pet shops are now just a click away"));
        pages.add(new OnBoardingPage(R.drawable.welcome_image,"TASK LIST","Create your own pet to-do list."));
        pages.add(new OnBoardingPage(R.drawable.welcome_image,"ADOPT PETS","Put up pets for adoption or connect with the ones you want to adopt."));
        pages.add(new OnBoardingPage(R.drawable.welcome_image,"LOST AND FOUND","Posting lost or found ads has never been easier."));

        OnBoarder.startOnBoarding(this, pages,null);

        OnBoarder.startOnBoarding(this, pages, new OnFinishLastPage() {
            @Override
            public void onNext() {
                startActivity(new Intent(this, AuthActivity.class));
            }
        });
    }
}