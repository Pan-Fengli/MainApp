package com.dalab.dalabapp.TrainingPages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class TraningHemostasis extends AppCompatActivity {
    private ImageView wound;
    private ImageView position;
    private ImageView left_white;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        InitUI();
    }
    private void InitUI()
    {
        left_white=findViewById(R.id.left_white);
    }

}
