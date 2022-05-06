package com.petme.app.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.PatternsCompat;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.petme.app.R;
import com.petme.app.databinding.FragmentLoginBinding;
import com.petme.app.base.BaseFragment;

import java.util.regex.Pattern;

public class LoginFragment extends BaseFragment < FragmentLoginBinding > {

	float v = 0;
	private FirebaseAuth mAuth;

	@NonNull
	@Override
	public FragmentLoginBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentLoginBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.username.setAlpha ( v );
		bind.loginPass.setAlpha ( v );
		bind.login.setAlpha ( v );
		bind.dontHaveAcc.setAlpha ( v );
		bind.signupLink.setAlpha ( v );

		bind.username.animate ( ).translationX ( 0 ).alpha ( 1 ).setDuration ( 800 ).setStartDelay ( 300 ).start ( );
		bind.loginPass.animate ( ).translationX ( 0 ).alpha ( 1 ).setDuration ( 800 ).setStartDelay ( 500 ).start ( );
		bind.login.animate ( ).translationX ( 0 ).alpha ( 1 ).setDuration ( 800 ).setStartDelay ( 500 ).start ( );
		bind.dontHaveAcc.animate ( ).translationX ( 0 ).alpha ( 1 ).setDuration ( 800 ).setStartDelay ( 700 ).start ( );
		bind.signupLink.animate ( ).translationX ( 0 ).alpha ( 1 ).setDuration ( 800 ).setStartDelay ( 700 ).start ( );

		bind.login.setOnClickListener(view1 -> signUp());

		bind.signupLink.setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).navigate ( R.id.goToSignUp ) );

		mAuth = FirebaseAuth.getInstance();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void signUp(){

		String email = bind.username.getText().toString().trim();
		String password = bind.loginPass.getText().toString().trim();

		if (email.isEmpty()){
			Toast.makeText(mCtx, "Email should not be empty", Toast.LENGTH_SHORT).show();
		}else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
			Toast.makeText(mCtx, "Enter a valid Email id", Toast.LENGTH_SHORT).show();
		}else if (password.isEmpty()){
			Toast.makeText(mCtx, "Password should not be empty", Toast.LENGTH_SHORT).show();
		}else{
			mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
				Toast.makeText(mCtx, "User Signed In : "+task.getResult().getUser().getEmail(), Toast.LENGTH_SHORT).show();
			}).addOnFailureListener(e -> {
				e.printStackTrace();
			});
		}
	}

	private void login(){

	}

}
