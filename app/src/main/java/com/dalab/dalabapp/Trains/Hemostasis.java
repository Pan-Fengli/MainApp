package com.dalab.dalabapp.Trains;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dalab.dalabapp.Adapter.hemostasisAdapter;
import com.dalab.dalabapp.HoeostasisDataPage;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.TrainingPages.TrainingHomeostasis;

import androidx.appcompat.app.AppCompatActivity;

public class Hemostasis extends AppCompatActivity {
    ListView mylist;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_page);
        mylist = findViewById(R.id.list);
        mylist.setAdapter(new hemostasisAdapter(Hemostasis.this));
        mylist.setOnItemClickListener(new MyOnItemClickListener());
        dialog = new AlertDialog.Builder(Hemostasis.this);
        dialog.setTitle("未检测到设备连接！");
        dialog.setMessage("请检查设备是否打开");

//        remindDialog = new AlertDialog.Builder(Hemostasis.this);
//        remindDialog.setTitle("准备好！");
//        remindDialog.setMessage("请先绑好止血带，然后点击开始即可");
//        remindDialog.setPositiveButton("开始", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent();
////                intent.setClass(Hemostasis.this, TrainingHomeostasis.class);
//                intent.setClass(Hemostasis.this, HoeostasisDataPage.class);
//                startActivity(intent);
//            }
//        });

    }
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
//            if(position == 0)
//            {
//                Intent intent = new Intent();
//                intent.setClass(Hemostasis.this, TrainingHomeostasis.class);
//                startActivity(intent);
//            }
//            else
//            {
//                dialog.show();
//            }
            Intent intent = new Intent();
//            intent.setClass(Hemostasis.this, TrainingHomeostasis.class);
            intent.setClass(Hemostasis.this, HoeostasisDataPage.class);
            intent.putExtra("TrainType", position);
            startActivity(intent);
        }
    }
}
