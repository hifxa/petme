package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.LostItemViewBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.LostModel;
import com.petme.app.utils.Alerts;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LostAdapter extends RecyclerView.Adapter<LostAdapter.LostAdapterHolder> {
    private final List<LostModel> mList;
    private final RecyclerClicks mClicks;
    private final Context mCtx;

    public LostAdapter(Context mCtx, List<LostModel> mList, RecyclerClicks mClicks) {
        this.mList = mList;
        this.mClicks = mClicks;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public LostAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LostAdapter.LostAdapterHolder(LostItemViewBinding.bind(LayoutInflater.from(mCtx).inflate(R.layout.lost_item_view, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull LostAdapter.LostAdapterHolder holder, int position) {

        try {
            LostModel lost = mList.get(position);

            Alerts.log("TAGS", "INSIDE RECYCLER " + lost.getPetAnimal());

            holder.bind.petBreed.setText(lost.getBreed());
            holder.bind.petAnimal.setText("Pet: " + lost.getPetAnimal());
            holder.bind.lastSeen.setText(lost.getLastSeen());
            holder.bind.details.setText(lost.getDetails());
            holder.bind.petContact.setText(lost.getContact());
            holder.bind.petDate.setText(formatTime(Long.parseLong(lost.getTimestamp())));

            Picasso.get().load(lost.getImage()).placeholder(R.drawable.pet).error(R.drawable.pet).into(holder.bind.petImg);
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

    public class LostAdapterHolder extends RecyclerView.ViewHolder {
        LostItemViewBinding bind;

        public LostAdapterHolder(@NonNull LostItemViewBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }
    }

}
