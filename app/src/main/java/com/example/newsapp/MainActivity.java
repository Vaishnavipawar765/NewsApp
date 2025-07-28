package com.example.newsapp;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addNewsBtn;
    RecyclerView recyclerView;
    DBHelper db;
    ArrayList<News> newsList;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addNewsBtn = findViewById(R.id.addNewsBtn);
        recyclerView = findViewById(R.id.recyclerView);

        db = new DBHelper(this);
        newsList = new ArrayList<>();
        loadData();

        addNewsBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, AddNewsActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Refresh list when coming back from Add/Edit
    }

    private void loadData() {
        newsList.clear();
        var cursor = db.getAllNews();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            newsList.add(new News(id, title, content));
        }
        adapter = new NewsAdapter(this, newsList, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
