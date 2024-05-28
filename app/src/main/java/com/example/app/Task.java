package com.example.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "task")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;
    private int priority;
    private Date updatedAt;



    public Task(String description, int priority, Date updatedAt) {
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
