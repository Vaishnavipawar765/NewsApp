package com.example.newsapp;



import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddNewsActivity extends AppCompatActivity {

    Button Submit;
    EditText titleEt, desEt;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_news);

        titleEt = findViewById(R.id.addTitle);
        desEt = findViewById(R.id.addDes);
        Submit = findViewById(R.id.SaveBtn);
        db = new DBHelper(this);

        // Ask Notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
        int editId = getIntent().getIntExtra("id", -1);

        if (isEdit && editId != -1) {
            // Editing existing news
            titleEt.setText(getIntent().getStringExtra("title"));
            desEt.setText(getIntent().getStringExtra("content"));
            Submit.setText("Update");

            Submit.setOnClickListener(v -> {
                String title = titleEt.getText().toString();
                String content = desEt.getText().toString();
                boolean updated = db.updateNews(editId, title, content);
                if (updated) {
                    Toast.makeText(this, "News Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            // Adding new news
            Submit.setOnClickListener(v -> {
                String title = titleEt.getText().toString();
                String content = desEt.getText().toString();
                if (db.insertNews(title, content)) {
                    NotificationHelper.showNotification(this, "Check our latest News", title);
                    Toast.makeText(this, "News Added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
