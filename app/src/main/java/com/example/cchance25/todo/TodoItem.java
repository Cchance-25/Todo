package com.example.cchance25.todo;

/**
 * Created by Cchance25 on 8/26/2017.
 */

public class TodoItem {

    private int mId;
    private String mTitle;
    private String mLocation;
    private String mDescription;
    private int mPriority;
    private boolean mDone;


    public TodoItem(String mTitle, String mLocation, String mDescription, int mPriority, boolean mDone) {
        this.mTitle = mTitle;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mPriority = mPriority;
        this.mDone = mDone;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmPriority() {
        return mPriority;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }

    public boolean ismDone() {
        return mDone;
    }

    public void setmDone(boolean mDone) {
        this.mDone = mDone;
    }
}
