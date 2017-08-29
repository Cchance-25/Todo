package com.example.cchance25.todo;

/**
 * Created by chance on 29/08/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chance on 27/08/17.
 */

public class LVAdapter extends BaseAdapter {


    private List<TodoItem> todoItemList;
    private Context context;

    public LVAdapter(Context c, List<TodoItem> list) {
        this.context = c;
        this.todoItemList = list;
    }

    public TodoItem getTodoItem(int position) {
        return todoItemList.get(position);
    }

    @Override
    public int getCount() {
        return todoItemList.size();
    }

    @Override
    public TodoItem getItem(int position) {
        return todoItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodoItem todoItem = getItem(position);
        ViewHolder v;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            v = new ViewHolder(convertView);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.todoTitleTextView.setText(todoItem.getmTitle());
        v.todoPriorityTextView.setText(String.valueOf(todoItem.getmPriority()));
        GradientDrawable circle = (GradientDrawable) v.todoPriorityTextView.getBackground();
        setColor(circle, todoItem.getmPriority());

        return convertView;
    }

    private void setColor(GradientDrawable circle, int priority) {
        circle.setColor(circleColor(priority));
    }

    private int circleColor(int priority) {
        switch (priority) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.DKGRAY;
            case 5:
                return Color.GREEN;
            default:
                return Color.GRAY;
        }

    }

    private class ViewHolder {
        TextView todoTitleTextView,
                todoPriorityTextView;
        CheckBox todoCheckBox;

        public ViewHolder(View itemView) {
            todoTitleTextView = (TextView) itemView.findViewById(R.id.tv_todo_title);
            todoPriorityTextView = (TextView) itemView.findViewById(R.id.tv_todo_priority);
            //todoLocationTextView = (TextView) itemView.findViewById(R.id.tv_todo_location);
            //todoDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_todo_description);
            todoCheckBox = (CheckBox) itemView.findViewById(R.id.cb_todo_done);
        }


    }
}
