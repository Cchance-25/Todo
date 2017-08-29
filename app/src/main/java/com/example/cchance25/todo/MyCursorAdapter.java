package com.example.cchance25.todo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cchance25.todo.Data.TodoContract;

/**
 * Created by chance on 29/08/17.
 */

public class MyCursorAdapter extends CursorAdapter {


    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView todoTitleTextView = (TextView) view.findViewById(R.id.tv_todo_title);
        TextView todoPriorityTextView = (TextView) view.findViewById(R.id.tv_todo_priority);


        int titleColumnIndex = cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_ITEM_TITLE);
        int priorityColumnIndex = cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_ITEM_PRIORITY);

        String titleValue = cursor.getString(titleColumnIndex);
        int priorityValue = cursor.getInt(priorityColumnIndex);

        todoTitleTextView.setText(titleValue);
        todoPriorityTextView.setText(String.valueOf(priorityValue));

        GradientDrawable drawable = (GradientDrawable) todoPriorityTextView.getBackground();
        drawable.setColor(circleColor(priorityValue));
    }

    private int circleColor(int priority) {
        switch (priority) {
            case 1:
                return Color.CYAN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.DKGRAY;
            case 4:
                return Color.MAGENTA;
            case 5:
                return Color.RED;
            default:
                return Color.GRAY;
        }
    }


}
