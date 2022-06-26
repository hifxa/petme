package com.petme.app.view.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.petme.app.R;

public class HeaderView extends LinearLayout {

	public ImageView back;
	public TextView title;
	public LinearLayout root;
	Context mCtx;

	public HeaderView ( Context context ) {
		super ( context );
		mCtx = context;
		init ( null );
	}

	public HeaderView ( Context context , @Nullable AttributeSet attrs ) {
		super ( context , attrs );
		mCtx = context;
		init ( attrs );
	}

	public HeaderView ( Context context , @Nullable AttributeSet attrs , int defStyleAttr ) {
		super ( context , attrs , defStyleAttr );
		mCtx = context;
		init ( attrs );
	}

	public HeaderView ( Context context , AttributeSet attrs , int defStyleAttr , int defStyleRes ) {
		super ( context , attrs , defStyleAttr , defStyleRes );
		mCtx = context;
		init ( attrs );
	}

	private void init ( @Nullable AttributeSet attrs ) {
		View view = inflate ( mCtx , R.layout.header_view , this );
		back = view.findViewById ( R.id.back );
		title = view.findViewById ( R.id.title );
		root = view.findViewById ( R.id.rootView );

		if ( attrs != null ) {
			TypedArray a = getContext ( ).obtainStyledAttributes ( attrs , R.styleable.HeaderView );

			try {
				ColorStateList mList = ContextCompat.getColorStateList ( mCtx , a.getResourceId ( R.styleable.HeaderView_bg_on_color , R.color.onPrimary ) );
				back.setImageTintList ( mList );
				title.setTextColor ( mList );
//				root.setBackgroundTintList ( ContextCompat.getColorStateList ( mCtx , a.getResourceId ( R.styleable.HeaderView_bg_color , R.color.primary ) ) );
				root.setBackgroundColor ( ContextCompat.getColor ( mCtx , a.getResourceId ( R.styleable.HeaderView_bg_color , R.color.primary ) ) );
			}
			catch ( Exception e ) {
				e.printStackTrace ( );
			}
			finally {
				a.recycle ( );
			}

		}
	}

	public ImageView getBack ( ) {
		return back;
	}

	public TextView getTitle ( ) {
		return title;
	}

	public LinearLayout getRootLayout ( ) {
		return root;
	}

}
