package com.dalab.dalabapp.Trains;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dalab.dalabapp.Adapter.bindAdapter;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.TrainingPages.TrainingBind;
import com.dalab.dalabapp.TrainingPages.TrainingHomeostasis;

import androidx.appcompat.app.AppCompatActivity;

public class BindPage extends AppCompatActivity {
    ListView myList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        myList = findViewById(R.id.list);
        myList.setAdapter(new bindAdapter(BindPage.this));
        myList.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Intent intent = new Intent();
            intent.setClass(BindPage.this, TrainingBind.class);
            intent.putExtra("TrainType", position);
            startActivity(intent);
        }
    }
}
