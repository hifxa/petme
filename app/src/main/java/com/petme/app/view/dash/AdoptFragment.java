package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.AdoptAdapter;
import com.petme.app.databinding.AddAdoptSheetBinding;
import com.petme.app.databinding.FragmentAdoptBinding;
import com.petme.app.model.AdoptModel;
import com.petme.app.model.TaskModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AdoptFragment extends BaseFragment<FragmentAdoptBinding> {

    DatabaseReference mRef = FirebaseDatabase.getInstance ( ).getReference ( "Adopt" );
    List<AdoptModel> mList = new ArrayList<>( );
    AdoptAdapter mAdapter;

    @Override
    public FragmentAdoptBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAdoptBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        bind.header.getTitle().setText("Adopt");

        bind.newAdoptButton.setOnClickListener ( view1 -> addAdoptAdv ( ) );
        mAdapter = new AdoptAdapter ( mCtx , mList , ( pos , type ) -> {

        } );
        bind.taskRecycler.setAdapter ( mAdapter );
    }

    public void addAdoptAdv(){

        AddAdoptSheetBinding sBind = AddAdoptSheetBinding.bind(getLayoutInflater().inflate(R.layout.add_adopt_sheet, bind.getRoot(), false));
        BottomSheetDialog mSheet = new BottomSheetDialog ( mCtx );
        mSheet.setContentView ( sBind.getRoot ( ) );
        mSheet.setDismissWithAnimation ( true );
        mSheet.setCancelable ( false );
        mSheet.setCanceledOnTouchOutside ( false );

        sBind.addAdoptionAdv.setOnClickListener(view -> {
            mSheet.dismiss();
            createAdoptAdv(sBind.petName.getText().toString().trim(), sBind.petBreed.getText().toString().trim(), sBind.age.getText().toString().trim(), sBind.miscDetails.getText().toString().trim(), sBind.contactInfo.getText().toString().trim());
        });

        sBind.close.setOnClickListener(view -> mSheet.dismiss());
        mSheet.show();
    }

    @Override
    public void onStart ( ) {
        super.onStart ( );

        getAds();
    }

    private void getAds ( ) {

        mRef.addValueEventListener ( new ValueEventListener( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snap ) {
                mList.clear ( );
                for ( DataSnapshot mData : snap.getChildren ( ) ) {
                    mList.add ( mData.getValue ( AdoptModel.class ) );
                }
                mAdapter.notifyDataSetChanged ( );
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {

            }
        } );
    }

   public void createAdoptAdv (String name, String breed, String age, String details, String contact ) {
       HashMap <String, String> adoptMap = new HashMap<>();
       adoptMap.put("name", name);
       adoptMap.put("breed", breed);
       adoptMap.put("age", age);
       adoptMap.put("details", details);
       adoptMap.put("contact", contact);
       adoptMap.put("timestamp", ""+ System.currentTimeMillis());

       String pushKey = mRef.push().getKey();
       mRef.child ( pushKey ).setValue ( adoptMap , (error , ref ) -> {
           if ( error == null ) {
               Alerts.success ( mCtx , "Task Added Successfully!" );
           }
           else {
               error.toException ( ).printStackTrace ( );
           }
       } );
   }

}