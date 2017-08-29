package com.example.cchance25.todo.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by chance on 27/08/17.
 */

public class TodoContract {

    // Content authority, content provider name
    public static final String CONTENT_AUTHORITY = "com.example.cchance25.todo";

    // Uri used to access this content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // possible path to access data
    public static final String PATH_TODO = "todolist";
    public static final int HIGHEST_PRIORITY = 1;

    /*
    *  Possible priority values
    *   1 being highest priority
    *   5 being lowest priority
    * */
    public static final int HIGH_PRIORITY = 2;
    public static final int NORMAL_PRIORITY = 3;
    public static final int LOW_PRIORITY = 4;
    public static final int LOWEST_PRIORITY = 5;

    private TodoContract() {
    }

    public static class TodoEntry implements BaseColumns {
        // URI (link) to access this table data
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TODO);

        // Table name
        public static final String TABLE_NAME = "todolist";

        // Table ID - Type Integer
        public static final String _ID = BaseColumns._ID;

        // _Todo item title - Type Text
        public static final String COLUMN_ITEM_TITLE = "title";

        // _Todo item location - Type Text
        public static final String COLUMN_ITEM_LOCATION = "location";

        // _Todo item description - Type Text
        public static final String COLUMN_ITEM_DESCRIPTION = "description";

        // _Todo item priority - Type Integer
        public static final String COLUMN_ITEM_PRIORITY = "priority";

        // _Todo item done or not - Type Integer
        public static final String COLUMN_ITEM_DONE = "done";


        public static boolean isValidPriority(int priority) {
            // true, when priority is between 1 and 5
            return (priority >= 1 && priority <= 5);
        }

    }
}