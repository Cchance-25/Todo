package com.example.cchance25.todo.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.cchance25.todo.Data.TodoContract.TodoEntry;

/**
 * Created by chance on 27/08/17.
 */

public class TodoProvider extends ContentProvider {

    private static final String LOG_TAG = TodoProvider.class.getSimpleName();

    // Uri matcher code for entire table
    private static final int TODOLIST = 100;

    // Uri matcher code for single item
    private static final int TODOLIST_ITEM = 101;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODO, TODOLIST);
        matcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODO + "/#", TODOLIST_ITEM);
    }

    private TodoDpHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new TodoDpHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = matcher.match(uri);

        switch (match) {
            case TODOLIST:
                cursor = db.query(TodoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        String.valueOf(TodoEntry.COLUMN_ITEM_PRIORITY),
                        null,
                        "ASC");
                break;
            case TODOLIST_ITEM:
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TodoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        String.valueOf(TodoEntry.COLUMN_ITEM_PRIORITY),
                        null,
                        "ASC");
                break;

            default:
                Log.e(LOG_TAG, "No match found for: " + uri);
                throw new IllegalArgumentException("Can't query unknown URI. " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = matcher.match(uri);
        switch (match) {
            case TODOLIST:
                return insertItem(uri, values);
            default:
                Log.e(LOG_TAG, "Insertion is not supported for: " + uri);
                throw new IllegalArgumentException("Insertion is not supported for: " + uri);

        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        String title = values.getAsString(TodoEntry.COLUMN_ITEM_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Todo title must not be empty. ");
        }

        String location = values.getAsString(TodoEntry.COLUMN_ITEM_LOCATION);
        if (location == null) {
            location = "Location is empty";
        }

        String description = values.getAsString(TodoEntry.COLUMN_ITEM_DESCRIPTION);
        if (description == null) {
            description = "No description!";
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insertOrThrow(TodoEntry.TABLE_NAME, null, values);
        if (id == 1) {
            Log.e(LOG_TAG, "Failed Adding: " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = matcher.match(uri);
        switch (match) {
            case TODOLIST:
                rowsDeleted = database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TODOLIST_ITEM:
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = matcher.match(uri);
        switch (match) {
            case TODOLIST:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case TODOLIST_ITEM:
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(TodoEntry.COLUMN_ITEM_TITLE)) {
            String title = values.getAsString(TodoEntry.COLUMN_ITEM_TITLE);
            if (title == null) {
                throw new IllegalArgumentException("Todo title must not be empty. ");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(TodoEntry.TABLE_NAME, values, selection, selectionArgs);


        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
