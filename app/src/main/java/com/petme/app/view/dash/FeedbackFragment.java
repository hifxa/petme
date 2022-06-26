package com.petme.app.view.dash;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentFeedbackBinding;

public class FeedbackFragment extends BaseFragment < FragmentFeedbackBinding > {


	@Override
	public FragmentFeedbackBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentFeedbackBinding.inflate ( inflater , container , false );
	}
}