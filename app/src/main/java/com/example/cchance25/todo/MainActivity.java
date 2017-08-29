package com.example.cchance25.todo;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cchance25.todo.Data.TodoContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lv;
    private MyCursorAdapter myCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.rv_todo_list);

        View em = findViewById(R.id.empty_view);
        lv.setEmptyView(em);

        myCursorAdapter = new MyCursorAdapter(this, null);
        lv.setAdapter(myCursorAdapter);

        getLoaderManager().initLoader(0, null, this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditorActivity.class);
                Uri currentUri = ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI, id);
                i.setData(currentUri);
                Log.e("TAG", currentUri.toString());
                startActivity(i);
            }
        });


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

        ContentValues cv = new ContentValues();
        cv.put(TodoContract.TodoEntry.COLUMN_ITEM_TITLE, "todo title");
        cv.put(TodoContract.TodoEntry.COLUMN_ITEM_LOCATION, "todo location");
        cv.put(TodoContract.TodoEntry.COLUMN_ITEM_DESCRIPTION, "todo description");
        cv.put(TodoContract.TodoEntry.COLUMN_ITEM_PRIORITY, TodoContract.NORMAL_PRIORITY);

        //getContentResolver().insert(TodoContract.TodoEntry.CONTENT_URI, cv);

        myCursorAdapter.notifyDataSetChanged();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                TodoContract.TodoEntry._ID,
                TodoContract.TodoEntry.COLUMN_ITEM_TITLE,
                TodoContract.TodoEntry.COLUMN_ITEM_PRIORITY
        };

        return new CursorLoader(this,
                TodoContract.TodoEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        myCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        myCursorAdapter.swapCursor(null);
    }


}




