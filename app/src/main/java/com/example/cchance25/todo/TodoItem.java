package com.example.cchance25.todo;

/**
 * Created by Cchance25 on 8/26/2017.
 */

public class TodoItem {

    private String title;
    private String location;
    private String description;
    private int priority;
    private boolean isDone;


    public TodoItem(String title, String location, String description, int priority, boolean isDone) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.priority = priority;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
