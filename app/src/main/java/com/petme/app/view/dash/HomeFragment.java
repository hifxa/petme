package com.petme.app.view.dash;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petme.app.databinding.FragmentHomeBinding;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.HomeOptionsAdapter;
import com.petme.app.view.custom.FadingTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends BaseFragment < FragmentHomeBinding > {

	List < String > homeOptions = new ArrayList <> ( Arrays.asList ( "vet" , "shop" , "tasks" , "lost/Found" , "adopt" , "mating" ) );
	HomeOptionsAdapter mAdapter;
	DatabaseReference dbRef;
	List<String> quotesList = new ArrayList<>();

	@NonNull
	@Override
	public FragmentHomeBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentHomeBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		mAdapter = new HomeOptionsAdapter ( mCtx , homeOptions );
		bind.optionsRecycler.setAdapter ( mAdapter );

		dbRef = FirebaseDatabase.getInstance().getReference("quotes");

		dbRef.get().addOnCompleteListener(task -> {
			if (task.isSuccessful()){
				DataSnapshot snap = task.getResult();

				for (DataSnapshot mData: snap.getChildren()) {
					quotesList.add(mData.getValue().toString());
					Log.d("TAG", "MDATA: "+mData.getValue());
				}
				bind.fadeText.setTexts(quotesList.toArray(new String[0]));
			}
		}).addOnFailureListener(e -> {

		});

		bind.fadeText.setTimeout(5, TimeUnit.SECONDS );

	}
}