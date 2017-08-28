package com.example.cchance25.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cchance25.todo.Data.TodoContract;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

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
                updateEntry();
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
