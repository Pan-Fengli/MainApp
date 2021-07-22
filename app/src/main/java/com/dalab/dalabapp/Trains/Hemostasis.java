package com.dalab.dalabapp.Trains;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dalab.dalabapp.Adapter.ListAdapter;
import com.dalab.dalabapp.Adapter.hemostasisAdapter;
import com.dalab.dalabapp.BlueTooth.BlueTooth;
import com.dalab.dalabapp.MainPage;
import com.dalab.dalabapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Hemostasis extends AppCompatActivity {
    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        mylist = findViewById(R.id.list);
        mylist.setAdapter(new hemostasisAdapter(Hemostasis.this));
    }

}
