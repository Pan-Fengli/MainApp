package com.dalab.dalabapp.Trains;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dalab.dalabapp.Adapter.bindAdapter;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.TrainingPages.TrainingBind;
import com.dalab.dalabapp.TrainingPages.TrainingHomeostasis;
import com.dalab.dalabapp.constant.Global;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

public class BindPage extends AppCompatActivity {
    TextView totalTime, tmp;
    ListView myList;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        totalTime = findViewById(R.id.detailListPageTotalTime);
//        totalTime.setText("包扎总时长" + Global.global.bindTime / 60 + "min");
        totalTime.setText("包扎训练总时长" + Global.global.bindTime  + "min");
        tmp = findViewById(R.id.detailListPageValidTime);
        tmp.setTextSize(0);
        myList = findViewById(R.id.list);
        myList.setAdapter(new bindAdapter(BindPage.this));
        myList.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Global.global.currentType = 10 + position;
            Intent intent = new Intent();
            intent.setClass(BindPage.this, TrainingBind.class);
            intent.putExtra("TrainType", position);
            startActivity(intent);
        }
    }
}
