package com.example.cchance25.todo;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cchance25.todo.Data.TodoContract;
import com.example.cchance25.todo.Data.TodoContract.TodoEntry;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    int priority = TodoContract.NORMAL_PRIORITY;
    private EditText mTitleEditText;
    private EditText mLocationEditText;
    private EditText mDescriptionEditText;
    private Spinner mPrioritySpinner;
    private Uri mCurrentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTitleEditText = (EditText) findViewById(R.id.et_todo_title);
        mLocationEditText = (EditText) findViewById(R.id.et_todo_location);
        mDescriptionEditText = (EditText) findViewById(R.id.et_description);
        mPrioritySpinner = (Spinner) findViewById(R.id.sp_todo_priority);

        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        if (mCurrentUri == null) {
            setTitle("Add todo");
        } else {
            setTitle("Edit todo");
            getLoaderManager().initLoader(0, null, this);
        }


        setupSpinner();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_task).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                return false;
            case R.id.action_save_task:
                saveEntry();
                finish();
                return true;
            case R.id.action_delete_task:
                deleteTask();
                finish();
                return true;
            default:
                return false;
        }
    }

    private void saveEntry() {
        String title = mTitleEditText.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String location = mLocationEditText.getText().toString().trim();
        if (TextUtils.isEmpty(location)) {
            location = "No location entered.";
        }

        String description = mDescriptionEditText.getText().toString().trim();
        if (TextUtils.isEmpty(description)) {
            description = "No description entered. ";
        }

        int priority = Integer.parseInt(mPrioritySpinner.getSelectedItem().toString());

        ContentValues cv = new ContentValues();

        cv.put(TodoEntry.COLUMN_ITEM_TITLE, title);
        cv.put(TodoEntry.COLUMN_ITEM_LOCATION, location);
        cv.put(TodoEntry.COLUMN_ITEM_DESCRIPTION, description);

        if (priority == TodoContract.HIGHEST_PRIORITY)
            priority = TodoContract.HIGHEST_PRIORITY;
        else if (priority == TodoContract.HIGH_PRIORITY)
            priority = TodoContract.HIGH_PRIORITY;
        else if (priority == TodoContract.NORMAL_PRIORITY)
            priority = TodoContract.NORMAL_PRIORITY;
        else if (priority == TodoContract.LOW_PRIORITY)
            priority = TodoContract.LOW_PRIORITY;
        else
            priority = TodoContract.LOWEST_PRIORITY;

        cv.put(TodoEntry.COLUMN_ITEM_PRIORITY, priority);

        if (mCurrentUri == null) {
            Uri newUri = getContentResolver().insert(TodoEntry.CONTENT_URI, cv);
            if (newUri == null) {
                Toast.makeText(this, "Error saving todo item! ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Todo item saved. ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("TAG", "mCurrentUri: (not null)" + mCurrentUri.toString());
            int rowsAffected = getContentResolver().update(mCurrentUri, cv, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "Error updating todo item! ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Current item changes saved. ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void deleteTask() {
        long result = getContentResolver().delete(mCurrentUri, null, null);
        if (result == -1) {
            Toast.makeText(this, "Deletion failed! ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Rows deleted:  " + result, Toast.LENGTH_SHORT).show();
        }

    }

    private void setupSpinner() {
        ArrayList<Integer> priorities = new ArrayList<>();

        priorities.add(TodoContract.HIGHEST_PRIORITY);
        priorities.add(TodoContract.HIGH_PRIORITY);
        priorities.add(TodoContract.NORMAL_PRIORITY);
        priorities.add(TodoContract.LOW_PRIORITY);
        priorities.add(TodoContract.LOWEST_PRIORITY);

        Spinner spinner = (Spinner) findViewById(R.id.sp_todo_priority);
        spinner.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        priorities));
        spinner.setSelection(priorities.get(1));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_ITEM_TITLE,
                TodoEntry.COLUMN_ITEM_DESCRIPTION,
                TodoEntry.COLUMN_ITEM_LOCATION,
                TodoEntry.COLUMN_ITEM_PRIORITY
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int titleColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_ITEM_TITLE);
            int descriptionColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_ITEM_DESCRIPTION);
            int locationColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_ITEM_LOCATION);
            int priorityColumnIndex = cursor.getColumnIndex(TodoEntry.COLUMN_ITEM_PRIORITY);

            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            String description = cursor.getString(descriptionColumnIndex);
            String location = cursor.getString(locationColumnIndex);
            priority = cursor.getInt(priorityColumnIndex);

            // Update the views on the screen with the values from the database
            mTitleEditText.setText(title);
            mDescriptionEditText.setText(description);
            mLocationEditText.setText(location);
            mPrioritySpinner.setSelection(priority - 1);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTitleEditText.setText("");
        mDescriptionEditText.setText("");
        mLocationEditText.setText("");
        mPrioritySpinner.setSelection(2);
    }
}
