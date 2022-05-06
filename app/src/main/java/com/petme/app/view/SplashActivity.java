package com.petme.app.view;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.petme.app.R;
import com.petme.app.databinding.ActivitySplashBinding;
import com.petme.app.utils.Prefs;
import com.petme.app.view.auth.AuthActivity;
import com.petme.app.view.dash.DashActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.splash.animate().alpha(1f).scaleX(2.2f).scaleY(2.2f).setDuration(1000).setInterpolator(new AnticipateOvershootInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (!new Prefs(SplashActivity.this).getUserId().equals("")) {
                    startActivity(new Intent(SplashActivity.this, DashActivity.class));
                    finishAfterTransition();
                } else {

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(() -> bind.onBoard.setVisibility(View.VISIBLE));
                        }
                    }, 800);

                    bind.splash.animate().alpha(0f).scaleX(1.2f).scaleY(1.2f).setDuration(900);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.surface));


        bind.onboardingButton.setOnClickListener(view -> {
            startActivity(new Intent(this, AuthActivity.class));
            finishAfterTransition();
        });
    }
}