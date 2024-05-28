package com.example.app;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EditorScreenViewModel extends ViewModel {


    private LiveData<Task> task;

    public EditorScreenViewModel(AppDataBase appDataBase, int id) {

        task = appDataBase.taskDao().getTaskById(id);
        Log.i(" Editor View Model "," Loading a task");

    }

    public LiveData<Task> getTask() {

        return task;
    }
}
