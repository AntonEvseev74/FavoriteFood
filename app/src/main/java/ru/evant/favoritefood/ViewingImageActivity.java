package ru.evant.favoritefood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.evant.favoritefood.adapter.ListItem;

public class ViewingImageActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_image);

        iv = findViewById(R.id.iv);
        Intent intent = getIntent();
        if(intent != null) {
            iv.setImageURI(Uri.parse(intent.getStringExtra("image")));
        }
    }
}
