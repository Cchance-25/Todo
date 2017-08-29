package com.example.cchance25.todo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cchance25.todo.Data.TodoContract;

/**
 * Created by chance on 29/08/17.
 */

public class RecyclerViewCursorAdapter extends CursorAdapter {

    TextView todoTitleTextView,
            todoPriorityTextView;
    CheckBox todoCheckBox;

    public RecyclerViewCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        todoTitleTextView = (TextView) view.findViewById(R.id.tv_todo_title);
        todoPriorityTextView = (TextView) view.findViewById(R.id.tv_todo_priority);
        //todoLocationTextView = (TextView) itemView.findViewById(R.id.tv_todo_location);
        //todoDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_todo_description);
        todoCheckBox = (CheckBox) view.findViewById(R.id.cb_todo_done);


        todoTitleTextView.setText(cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_ITEM_TITLE)));
        todoTitleTextView.setText(cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_ITEM_PRIORITY)));

        int doneValue = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_ITEM_DONE));

        boolean done = doneValue == 1;

        todoCheckBox.setChecked(done);


    }

}
