package com.example.cchance25.todo.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cchance25.todo.Data.TodoContract.TodoEntry;

/**
 * Created by chance on 27/08/17.
 */

public class TodoDpHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = TodoDpHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public TodoDpHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TodoEntry.TABLE_NAME + "( "
                + TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TodoEntry.COLUMN_ITEM_NUMBER + " INTEGER AUTOINCREMENT, "
                + TodoEntry.COLUMN_ITEM_TITLE + " TEXT NOT NULL, "
                + TodoEntry.COLUMN_ITEM_LOCATION + " TEXT, "
                + TodoEntry.COLUMN_ITEM_DESCRIPTION + " TEXT, "
                + TodoEntry.COLUMN_ITEM_PRIORITY + " INTEGER DEFAULT "
                + TodoContract.NORMAL_PRIORITY + ");";

        db.execSQL(sql);
        Log.i(LOG_TAG, "Database created successfully. ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
