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
import com.petme.app.controllers.FoundAdapter;
import com.petme.app.controllers.LostAdapter;
import com.petme.app.databinding.FragmentLostBinding;
import com.petme.app.model.FoundModel;
import com.petme.app.model.LostModel;
import com.petme.app.utils.Alerts;

import java.util.ArrayList;
import java.util.List;

public class LostFragment extends BaseFragment<FragmentLostBinding> {

    List<LostModel> mList = new ArrayList<>();
    LostAdapter mAdapter;
    NavController ctrl;

    public LostFragment(NavController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public FragmentLostBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLostBinding.inflate(inflater,container,false);
    }

    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );


        bind.newLostButton.setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).navigate ( R.id.createLostFragment ) );

        mAdapter = new LostAdapter( mCtx , mList , (pos , type ) -> {
        } );

        bind.LostRecycler.setAdapter ( mAdapter );
    }

    @Override
    public void onStart ( ) {
        super.onStart();


        fetchLostAds();
    }

    public void fetchLostAds(){
        FireRef.lostDbRef.addValueEventListener ( new ValueEventListener( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snap ) {
                mList.clear ( );
                for ( DataSnapshot mData : snap.getChildren ( ) ) {

                    Alerts.log ( TAG , "DATA: " + mData.getValue ( ) );

                    mList.add ( mData.getValue ( LostModel.class ) );
                }
                mAdapter.notifyDataSetChanged ( );
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {

            }
        } );
    }

}