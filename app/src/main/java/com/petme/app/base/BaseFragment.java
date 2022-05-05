package com.petme.app.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<BIND extends ViewBinding > extends Fragment {

	protected BIND bind;
	protected Context mCtx;

	@Nullable
	@Override
	public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
		bind = getBind ( inflater, container );
		mCtx = inflater.getContext ();

		return bind.getRoot ();
	}


	public abstract BIND getBind(@NonNull LayoutInflater inflater , @Nullable ViewGroup container );

}
