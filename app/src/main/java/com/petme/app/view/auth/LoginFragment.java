package com.petme.app.view.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentLoginBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;
import com.petme.app.view.dash.DashActivity;

import java.util.HashMap;

public class LoginFragment extends BaseFragment < FragmentLoginBinding > {

	float v = 0;
	private String id = "0";
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

		bind.login.setOnClickListener ( view1 -> {
			String email = bind.username.getText ( ).toString ( ).trim ( );
			String password = bind.loginPass.getText ( ).toString ( ).trim ( );

			if ( email.isEmpty ( ) ) {
				Alerts.error ( mCtx , "Email should not be empty" );
			}
			else if ( ! PatternsCompat.EMAIL_ADDRESS.matcher ( email ).matches ( ) ) {
				Alerts.error ( mCtx , "Invalid Email Id" );
			}
			else if ( password.isEmpty ( ) ) {
				Alerts.error ( mCtx , "Password should not be empty" );
			}
			else {
				loginUser ( email , password );
			}
		} );

		bind.signupLink.setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).navigate ( R.id.goToSignUp ) );

		mAuth = FirebaseAuth.getInstance ( );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
	}

	// this is called twice, once when user clicks the button, and then once a new user is added
	private void loginUser ( String email , String password ) {
		bind.loader.setVisibility ( View.VISIBLE );
		mAuth.signInWithEmailAndPassword ( email , password ).addOnCompleteListener ( task -> {
			if ( task.isSuccessful ( ) ) {
				Alerts.log ( TAG , "User Logged In: " + task.getResult ( ).getUser ( ).getEmail ( ) );
				onLoginSuccess ( task.getResult ( ).getUser ( ) , email );
			}
			else {
				// here the exception is simple that the user added is invalid and doesn't exist so we'll just register that user and log them in
				if ( task.getException ( ) instanceof FirebaseAuthInvalidUserException ) {
					signIn ( email , password );
				}
			}
		} ).addOnFailureListener ( e -> {
			bind.loader.setVisibility ( View.GONE );

			Alerts.showAlert ( mCtx , "Oops!" , e.getLocalizedMessage ( ) , true , false , new AlertClicks ( ) {
				@Override
				public void positiveClick ( DialogInterface alert ) {
					Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
				}

				@Override
				public void negativeClick ( DialogInterface alert ) {
					Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
				}
			} );

			e.printStackTrace ( );
		} );
	}

	// if while logging in the user doesn't exist this is called to create the new user
	private void signIn ( String email , String password ) {
		mAuth.createUserWithEmailAndPassword ( email , password ).addOnCompleteListener ( task -> {
			if ( task.isSuccessful ( ) ) {
				Alerts.log ( TAG , "User Signed Up: " + task.getResult ( ).getUser ( ).getEmail ( ) );

				loginUser ( email , password );
			}
		} ).addOnFailureListener ( e -> {
			e.printStackTrace ( );
		} );
	}

	// after a successful login, the user details are saved here.
	private void onLoginSuccess ( FirebaseUser mUser , String email ) {

		FireRef.userDbRef.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snapshot ) {
				id = "" + snapshot.getChildrenCount ( );

				Alerts.log ( TAG , "COUNT: " + snapshot.getChildrenCount ( ) );

				bind.loader.setVisibility ( View.GONE );

				HashMap < String, Object > userMap = new HashMap <> ( );
				userMap.put ( "email" , email );
				userMap.put ( "user_id" , mUser.getUid ( ) );
				userMap.put ( "id" , id );
				userMap.put ( "name" , mUser.getDisplayName ( ) != null ? mUser.getDisplayName ( ) : "" + email.split ( "@" )[ 0 ] );
				userMap.put ( "image" , "" );

				FireRef.userDbRef.child ( mUser.getUid ( ) ).updateChildren ( userMap ).addOnSuccessListener ( task -> {
					Prefs mPref = new Prefs ( mCtx );
					mPref.putString ( Prefs.USER_ID , mUser.getUid ( ) );
					mPref.putString ( Prefs.ID , id );
					mPref.putString ( Prefs.USER_NAME , mUser.getDisplayName ( ) != null ? mUser.getDisplayName ( ) : "" + email.split ( "@" )[ 0 ] );
					mPref.putString ( Prefs.USER_EMAIL , mUser.getEmail ( ) );

					startActivity ( new Intent ( mCtx , DashActivity.class ).setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
					( ( AppCompatActivity ) mCtx ).finishAfterTransition ( );
				} ).addOnFailureListener ( e ->
						e.printStackTrace ( )
				);
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );

	}
}
