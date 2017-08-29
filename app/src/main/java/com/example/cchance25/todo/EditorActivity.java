package com.example.cchance25.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cchance25.todo.Data.TodoContract;
import com.example.cchance25.todo.Data.TodoContract.TodoEntry;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

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
                // finish();
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

        TodoItem currentItem = new TodoItem();

        String title = mTitleEditText.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        currentItem.setmTitle(title);

        String location = mLocationEditText.getText().toString().trim();
        if (TextUtils.isEmpty(location)) {
            location = "No location entered.";
        }
        currentItem.setmLocation(location);

        String description = mDescriptionEditText.getText().toString().trim();
        if (TextUtils.isEmpty(description)) {
            description = "No description entered. ";
        }
        currentItem.setmDescription(description);

        int priority = Integer.parseInt(mPrioritySpinner.getSelectedItem().toString());

        ContentValues cv = new ContentValues();

        cv.put(TodoEntry.COLUMN_ITEM_TITLE, currentItem.getmTitle());
        cv.put(TodoEntry.COLUMN_ITEM_LOCATION, currentItem.getmLocation());
        cv.put(TodoEntry.COLUMN_ITEM_DESCRIPTION, currentItem.getmDescription());

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

        currentItem.setmPriority(priority);
        cv.put(TodoEntry.COLUMN_ITEM_PRIORITY, currentItem.getmPriority());

        if (mCurrentUri == null) {
            Uri newUri = getContentResolver().insert(TodoEntry.CONTENT_URI, cv);

            if (newUri == null) {
                Toast.makeText(this, "Error saving todo item! ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Todo item saved. ", Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentUri, cv, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "Error updating todo item! ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Current item changes saved. ", Toast.LENGTH_SHORT).show();

            }

        }


    }

    private void deleteTask() {
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
}
