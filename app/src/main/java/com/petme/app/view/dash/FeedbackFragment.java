package com.petme.app.view.dash;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentFeedbackBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.utils.Alerts;

import java.util.HashMap;

public class FeedbackFragment extends BaseFragment < FragmentFeedbackBinding > {


	@Override
	public FragmentFeedbackBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentFeedbackBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		bind.header.getBack ( ).setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "About Us" );

		bind.feedbackBtn.setOnClickListener(view1 -> sendFeedback());

	}

	private void sendFeedback(){
		hideKeyboard ();

		HashMap<String,String> feedback = new HashMap<>();
		feedback.put("name",bind.name.getText().toString().trim());
		feedback.put("number",bind.email.getText().toString().trim());
		feedback.put("message",bind.message.getText().toString().trim());

		String key = FireRef.feedbackRef.push().getKey();

		FireRef.feedbackRef.child(key).setValue(feedback, ( error , ref ) -> {
			if ( error == null ) {

				Alerts.showAlert ( mCtx , "Success!" , "Your Feedback has been sent to Us!" , false , false , new AlertClicks( ) {
					@Override
					public void positiveClick ( DialogInterface alert ) {
						alert.dismiss ( );
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}

					@Override
					public void negativeClick ( DialogInterface alert ) {
						alert.dismiss ( );
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}
				} );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		});

	}

}