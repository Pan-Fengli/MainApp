package com.dalab.dalabapp.Trains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dalab.dalabapp.Adapter.hemostasisAdapter;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.TrainingPages.TrainingHomeostasis;
import com.dalab.dalabapp.constant.Global;

import androidx.appcompat.app.AppCompatActivity;

public class Hemostasis extends AppCompatActivity {
    TextView validTime, totalTime;
    ListView mylist;
    AlertDialog.Builder dialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        // 设置总时间和有效时间
        validTime = findViewById(R.id.detailListPageValidTime);
        totalTime = findViewById(R.id.detailListPageTotalTime);
//        validTime.setText("有效止血时间" + Global.global.hoeoValidTime / 60 + "min");
//        totalTime.setText("总止血时间" + Global.global.hoeoTime / 60 + "min");
        validTime.setText("有效止血时间" + Global.global.hoeoValidTime + "min");
        totalTime.setText("总止血训练时长" + Global.global.hoeoTime + "min");
        validTime.setVisibility(View.VISIBLE);
        totalTime.setVisibility(View.VISIBLE);
        // others
        mylist = findViewById(R.id.list);
        mylist.setAdapter(new hemostasisAdapter(Hemostasis.this));
        mylist.setOnItemClickListener(new MyOnItemClickListener());
        dialog = new AlertDialog.Builder(Hemostasis.this);
        dialog.setTitle("未检测到设备连接！");
        dialog.setMessage("请检查设备是否打开");

    }
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            Global.global.currentType = 20 + position;
            intent.setClass(Hemostasis.this, TrainingHomeostasis.class);
//            intent.setClass(Hemostasis.this, HoeostasisDataPage.class);
            intent.putExtra("TrainType", position);
            startActivity(intent);
        }
    }
}
