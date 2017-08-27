package com.example.cchance25.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<TodoItem> list;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        setupFakeList();
        setupRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                addSingleFakeDataRow();
                return true;
        }
        return true;
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

    private void setupRecyclerView() {
        rv = (RecyclerView) findViewById(R.id.rv_todo_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        adapter = new ListAdapter(list, this);
        rv.addItemDecoration(did);
        rv.setAdapter(adapter);
    }




}
