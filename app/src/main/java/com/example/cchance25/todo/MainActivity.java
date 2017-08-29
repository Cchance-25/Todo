package com.example.cchance25.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cchance25.todo.Data.TodoContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView rv;
    private List<TodoItem> list;
    private RecyclerViewCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        rv = (RecyclerView) findViewById(R.id.rv_todo_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        adapter = new RecyclerViewCursorAdapter(this, null);
        rv.addItemDecoration(did);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_delete_task).setVisible(false);
        menu.findItem(R.id.action_save_task).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                addSingleFakeDataRow();
                Intent i = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }

    private void addSingleFakeDataRow() {
        list.add(new TodoItem("Fake todo", // title
                "Fake Location", // location
                "Fake description",  // description
                1, // fake priority
                false) // fake not done
        );
        Toast.makeText(this, "New row added", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

    }

    private void setupFakeList() {
        list.add(new TodoItem("Buy Milk", "Panda", "Milk", 1, false));
        list.add(new TodoItem("Buy Eggs", "Othiam", "Eggs", 2, false));
        list.add(new TodoItem("Buy Pastries", "Sanabel Assalam", "Mix", 3, true));
        list.add(new TodoItem("Buy Vanilla", "Panda", "Vanilla", 4, false));
        list.add(new TodoItem("Go to the Gym", "Sahafa", "Fitness time", 5, true));
        list.add(new TodoItem("Wash the car", "Malaz", "Marn", 9, false));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                TodoContract.TodoEntry.COLUMN_ITEM_TITLE
        };

        return new CursorLoader(this,
                TodoContract.TodoEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }




}




