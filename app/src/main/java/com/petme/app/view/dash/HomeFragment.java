package com.petme.app.view.dash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.HomeOptionsAdapter;
import com.petme.app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressLint ( "SetTextI18n" )
public class HomeFragment extends BaseFragment < FragmentHomeBinding > {

    List < String > homeOptions = new ArrayList <> ( Arrays.asList ( "vet" , "shop" , "tasks" , "lost/Found" , "adopt" , "mating" ) );
    List < String > quotesList = new ArrayList <> ( );
    HomeOptionsAdapter mAdapter;
    DatabaseReference dbRef;

    @Override
    public FragmentHomeBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
        return FragmentHomeBinding.inflate ( inflater , container , false );
    }


    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );

        mAdapter = new HomeOptionsAdapter ( mCtx , homeOptions , ( pos , type ) -> {
            switch ( type ) {
                case "vet":
                    requestPerms ( granted -> {
                        if ( granted ) {
                            Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToVetFragment );
                        }
                    } );
                    break;
                case "shop":
                    Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToShopsFragment );
                    break;
                case "tasks":
                    Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToTaskFragment );
                    break;
                case "lost/Found":
                    Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToLostFoundFragment );
                    break;
                case "adopt":
                    Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToAdoptFragment );
                    break;
                case "mating":
                    Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToMatingFragment );
                    break;
            }
        } );

        bind.optionsRecycler.setAdapter ( mAdapter );

        dbRef = FirebaseDatabase.getInstance ( ).getReference ( "quotes" );

        dbRef.get ( ).addOnCompleteListener ( task -> {
            if ( task.isSuccessful ( ) ) {
                DataSnapshot snap = task.getResult ( );
                for ( DataSnapshot mData : snap.getChildren ( ) ) {
                    quotesList.add ( mData.getValue ( ).toString ( ) );
                }
                bind.fadeText.setTexts ( quotesList.toArray ( new String[ 0 ] ) );
            }
        } ).addOnFailureListener ( e -> {
            e.printStackTrace ( );
        } );

        bind.fadeText.setTimeout ( 5 , TimeUnit.SECONDS );
    }

}