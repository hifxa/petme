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

import com.google.android.material.transition.MaterialFadeThrough;
import com.permissionx.guolindev.PermissionX;
import com.petme.app.R;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Consts;

import java.util.Locale;

public abstract class BaseFragment < BIND extends ViewBinding > extends Fragment {

	protected String TAG = "_TAG_";
	protected Context mCtx;
	protected BIND bind;

	public abstract BIND getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container );

	@Override
	public void onCreate ( @Nullable Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );

		setEnterTransition ( new MaterialFadeThrough ( ) );
		setExitTransition ( new MaterialFadeThrough ( ) );
	}

	@Override
	public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
		bind = getBind ( inflater , container );
		mCtx = inflater.getContext ( );

		if ( getTag ( ) != null ) {
			TAG = getTag ( ).toUpperCase ( Locale.ROOT );
		}

		return bind.getRoot ( );
	}

	protected void requestPerms ( PermissionCallback func ) {
		PermissionX.init ( requireActivity ( ) )
				.permissions ( Consts.getPerms ( ) )
				.explainReasonBeforeRequest ( )
				.onExplainRequestReason ( ( scope , deniedList ) -> {
					scope.showRequestReasonDialog ( deniedList , getString ( R.string.rationale ) , "OK" , "Cancel" );
				} )
				.onForwardToSettings ( ( scope , deniedList ) -> {
					scope.showForwardToSettingsDialog ( deniedList , "You need to allow necessary permissions in Settings manually" , "OK" , "Cancel" );
				} )
				.request ( ( allGranted , grantedList , deniedList ) -> {
					if ( allGranted ) {
						Alerts.log ( TAG , "GRANTED : $grantedList" );
						func.onGranted ( true );
					} else {
						Alerts.log ( TAG , "DENIED : $deniedList" );
						func.onGranted ( false );
					}
				} );
	}

	public interface PermissionCallback {
		void onGranted ( boolean granted );
	}

}