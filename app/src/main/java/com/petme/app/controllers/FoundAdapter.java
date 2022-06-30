package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.FoundItemViewBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.FoundModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.FoundAdapterHolder> {

    private final List<FoundModel> mList;
    private final RecyclerClicks mClicks;
    private final Context mCtx;

    public FoundAdapter(Context mCtx, List<FoundModel> mList, RecyclerClicks mClicks) {
        this.mList = mList;
        this.mClicks = mClicks;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public FoundAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoundAdapter.FoundAdapterHolder(FoundItemViewBinding.bind(LayoutInflater.from(mCtx).inflate(R.layout.found_item_view, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull FoundAdapterHolder holder, int position) {

        try {
            FoundModel found = mList.get(position);
            holder.bind.petBreed.setText(found.getBreed());
            holder.bind.petAnimal.setText("Pet: " + found.getPetAnimal());
            holder.bind.foundLocation.setText(found.getFoundLocation());
            holder.bind.currentlyAt.setText(found.getCurrentlyAt());
            holder.bind.petContact.setText(found.getContact());
            holder.bind.petDate.setText(formatTime(Long.parseLong(found.getTimestamp())));

            Picasso.get().load(found.getImage()).placeholder(R.drawable.pet).error(R.drawable.pet).into(holder.bind.petImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatTime(long parseLong) {
        try {
            return new SimpleDateFormat("dd MM,yyyy, hh:mm a", Locale.getDefault()).format(new Date(parseLong));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FoundAdapterHolder extends RecyclerView.ViewHolder {
        FoundItemViewBinding bind;

        public FoundAdapterHolder(@NonNull FoundItemViewBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }
    }
}
