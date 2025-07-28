package com.example.newsapp;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<News> newsList;
    DBHelper db;

    public NewsAdapter(Context context, ArrayList<News> newsList, DBHelper db) {
        this.context = context;
        this.newsList = newsList;
        this.db = db;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.titleText.setText(news.getTitle());
        holder.contentText.setText(news.getContent());

        // Delete button logic
        holder.deleteBtn.setOnClickListener(v -> {
            boolean deleted = db.deleteNews(news.getId());
            if (deleted) {
                newsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, newsList.size());
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit button logic
        holder.editBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, AddNewsActivity.class);
            i.putExtra("isEdit", true);
            i.putExtra("id", news.getId());
            i.putExtra("title", news.getTitle());
            i.putExtra("content", news.getContent());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, contentText;
        Button editBtn, deleteBtn;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            contentText = itemView.findViewById(R.id.contentText);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
