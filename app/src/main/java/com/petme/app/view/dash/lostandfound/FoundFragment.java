package com.petme.app.view.dash.lostandfound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.AdoptAdapter;
import com.petme.app.controllers.FoundAdapter;
import com.petme.app.databinding.FragmentFoundBinding;
import com.petme.app.model.AdoptModel;
import com.petme.app.model.FoundModel;
import com.petme.app.utils.Alerts;

import java.util.ArrayList;
import java.util.List;


public class FoundFragment extends BaseFragment<FragmentFoundBinding> {

    List<FoundModel> mList = new ArrayList<>( );
    FoundAdapter mAdapter;
    NavController ctrl;

    public FoundFragment(NavController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public FragmentFoundBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFoundBinding.inflate(inflater,container,false);
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );


        bind.newFoundButton.setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).navigate ( R.id.createAdoptionFragment ) );

        mAdapter = new FoundAdapter( mCtx , mList , (pos , type ) -> {
        } );

        bind.FoundRecycler.setAdapter ( mAdapter );
    }

    @Override
    public void onStart ( ) {
        super.onStart();
        getAds();

        fetchAdoptions();
    }

    private void getAds ( ) {

        FireRef.adoptDbRef.addValueEventListener ( new ValueEventListener( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snap ) {
                mList.clear ( );
                for ( DataSnapshot mData : snap.getChildren ( ) ) {
                    mList.add ( mData.getValue ( FoundModel.class ) );
                }
                mAdapter.notifyDataSetChanged ( );
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {

            }
        } );
    }

    private void fetchAdoptions ( ) {
        FireRef.adoptDbRef.addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snap ) {
                mList.clear ( );
                for ( DataSnapshot mData : snap.getChildren ( ) ) {

                    Alerts.log ( TAG , "DATA: " + mData.getValue ( ) );

                    mList.add ( mData.getValue ( FoundModel.class ) );
                }
                mAdapter.notifyDataSetChanged ( );
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {

            }
        } );
    }

}