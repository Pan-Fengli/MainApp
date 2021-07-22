package com.dalab.dalabapp.Trains;

import android.os.Bundle;
import android.widget.ListView;

import com.dalab.dalabapp.Adapter.bindAdapter;
import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class BindPage extends AppCompatActivity {
    ListView myList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        myList = findViewById(R.id.list);
        myList.setAdapter(new bindAdapter(BindPage.this));
    }
}
