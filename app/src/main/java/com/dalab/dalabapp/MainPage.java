package com.dalab.dalabapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dalab.dalabapp.Adapter.ListAdapter;
import com.dalab.dalabapp.Animation.Animation;
import com.dalab.dalabapp.BlueTooth.BlueTooth;
import com.dalab.dalabapp.Trains.BindPage;
import com.dalab.dalabapp.Trains.Hemostasis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainPage extends AppCompatActivity {
    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        mylist = findViewById(R.id.list);
        mylist.setAdapter(new ListAdapter(MainPage.this));
        mylist.setOnItemClickListener(
                new MyOnItemClickListener()
        );
        FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(MainPage.this, BlueTooth.class);
                        startActivity(intent);
                    }
                }
        );
    }
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            if(position == 0)
            {
                intent.setClass(MainPage.this, Hemostasis.class);
                startActivity(intent);
            }
            else
            {
                intent.setClass(MainPage.this, BindPage.class);
                startActivity(intent);
            }
        }
    }
}
