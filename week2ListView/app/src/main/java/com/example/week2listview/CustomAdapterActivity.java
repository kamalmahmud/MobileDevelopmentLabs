package com.example.week2listview;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {

    final List<Animal> animals = new ArrayList<Animal>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_adapter);
       animals.add(new Animal("Ant",R.mipmap.ic_launcher_round));
        animals.add(new Animal("Bear",R.mipmap.ic_launcher));
        animals.add(new Animal("Bird",R.mipmap.ic_launcher));
        animals.add(new Animal("Cat",R.mipmap.ic_launcher));
        animals.add(new Animal("Lion",R.mipmap.ic_launcher));
        animals.add(new Animal("Tiger",R.mipmap.ic_launcher));
        animals.add(new Animal("Dog",R.mipmap.ic_launcher));

        final ListView listView = findViewById(R.id.listview);
        AnimalAdapter animalAdapter = new AnimalAdapter(this,animals);
        listView.setAdapter(animalAdapter);



    }
}