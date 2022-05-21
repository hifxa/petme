package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentTaskBinding;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.HashMap;

public class TaskFragment extends BaseFragment<FragmentTaskBinding> {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Task");

    @Override
    public FragmentTaskBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentTaskBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(v -> {
            if (bind.taskView.isShown()) {
                bind.taskView.animate().translationY(0f).setDuration(300);
                bind.taskView.setVisibility(View.GONE);

            } else {
                Navigation.findNavController(v).popBackStack();
            }
        });
        bind.header.getTitle().setText("My Tasks");

        bind.newTaskButton.setOnClickListener(view1 -> {
                    if (bind.taskView.isShown()) {
                        bind.taskView.animate().translationY(0f).setDuration(300);
                        bind.taskView.setVisibility(View.GONE);

                    } else {
                        bind.taskView.animate().translationY(1000f).setDuration(300);
                        bind.taskView.setVisibility(View.VISIBLE);
                    }
                });
        bind.addtask.setOnClickListener(view1 -> {
            addATask(bind.petName.getText().toString().trim(),bind.taskTitle.getText().toString().trim(),bind.desc.getText().toString().trim(), "Pending");
        });
    }

    private void addATask(String name, String title, String desc, String status) {
        HashMap<String, String> taskMap = new HashMap<>();
        taskMap.put("name", name);
        taskMap.put("task", title);
        taskMap.put("desc", desc);
        taskMap.put("timeStamp", "" + System.currentTimeMillis());
        taskMap.put("status", status);

        String pushKey = mRef.getKey();

        mRef.child(new Prefs(mCtx).getUserId()).child(pushKey).setValue(taskMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    bind.taskView.animate().translationY(0f).setDuration(300);
                    bind.taskView.setVisibility(View.GONE);
                    Alerts.success(mCtx, "Task Added Successfully!");
                }
                else{
                    error.toException().printStackTrace();
                }
            }
        });
    }
}