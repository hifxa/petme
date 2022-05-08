package com.petme.app.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.ybq.android.spinkit.SpinKitView;
import com.petme.app.R;

public class HeaderView extends LinearLayout {

	Context mCtx;

	public HeaderView(Context context ) {
		super ( context );
		mCtx = context;
		init ( );
	}

	public HeaderView(Context context , @Nullable AttributeSet attrs ) {
		super ( context , attrs );
		mCtx = context;
		init ( );
	}

	public HeaderView(Context context , @Nullable AttributeSet attrs , int defStyleAttr ) {
		super ( context , attrs , defStyleAttr );
		mCtx = context;
		init ( );
	}

	public HeaderView(Context context , AttributeSet attrs , int defStyleAttr , int defStyleRes ) {
		super ( context , attrs , defStyleAttr , defStyleRes );
		mCtx = context;
		init ( );
	}

	ImageView back;
	TextView title;

	private void init ( ) {
		View view = inflate ( mCtx , R.layout.header_view , this );
		back = view.findViewById ( R.id.back );
		title = view.findViewById ( R.id.title );

	}
}
