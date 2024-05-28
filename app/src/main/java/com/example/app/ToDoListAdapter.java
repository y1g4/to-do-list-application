package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TaskViewHolder>{
    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";


    // Class variables for the List that holds task data and the Context
    private List<Task> mTaskEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public ToDoListAdapter(Context context) {
        mContext = context;
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_row_item, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        Task taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        final int id = taskEntry.getId(); // get item id
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        // Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        Drawable background = holder.priorityView.getBackground();
        if (background instanceof GradientDrawable) {
            GradientDrawable priorityCircle = (GradientDrawable) background;
            // Get the appropriate background color based on the priority
            int priorityColor = getPriorityColor(priority);
            priorityCircle.setColor(priorityColor);
        } else {
            Log.e("DrawableError", "Drawable is not a GradientDrawable");
        }

        // set Onclick Listener
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
            }
        });
    }



    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }


    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Task> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public List<Task> getTasks() {
        return mTaskEntries;
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;

        View view;




        // Constructor for the TaskViewHolders.

        public TaskViewHolder(View itemView) {
            super(itemView);


            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            view = itemView;
        }


    }
}
