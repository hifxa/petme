package com.petme.app.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.ybq.android.spinkit.SpinKitView;
import com.petme.app.R;

public class Loader extends LinearLayout {

	Context mCtx;

	public Loader ( Context context ) {
		super ( context );
		mCtx = context;
		init ( );
	}

	public Loader ( Context context , @Nullable AttributeSet attrs ) {
		super ( context , attrs );
		mCtx = context;
		init ( );
	}

	public Loader ( Context context , @Nullable AttributeSet attrs , int defStyleAttr ) {
		super ( context , attrs , defStyleAttr );
		mCtx = context;
		init ( );
	}

	public Loader ( Context context , AttributeSet attrs , int defStyleAttr , int defStyleRes ) {
		super ( context , attrs , defStyleAttr , defStyleRes );
		mCtx = context;
		init ( );
	}

	private void init ( ) {
		View view = inflate ( mCtx , R.layout.loader_view , this );
		SpinKitView loader = view.findViewById ( R.id.spinner );

	}
}
