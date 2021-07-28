package com.dalab.dalabapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dalab.dalabapp.Adapter.ListAdapter;
import com.dalab.dalabapp.Animation.Animation;
import com.dalab.dalabapp.BlueTooth.BlueTooth;
import com.dalab.dalabapp.Trains.BindPage;
import com.dalab.dalabapp.Trains.Hemostasis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainPage extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    LinearLayout mDrawerContent;
    EditText lowerBound;
    EditText upperBound;
    public static int lowerValue = 200;
    public static int upperValue = 600;

    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_page);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        setSupportActionBar(toolbar);//用我们自定义的toolbar

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

        mDrawerContent=findViewById(R.id.right_drawer);

        lowerBound = findViewById(R.id.lowerBound);
        upperBound = findViewById(R.id.upperBound);
        lowerBound.setText(String.valueOf(lowerValue));
        upperBound.setText(String.valueOf(upperValue));
        setFoucus(lowerBound);
        setFoucus(upperBound);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//填充菜单
        //这样就可以保证不会出问题！
        return true;
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
    private void setFoucus(final EditText et)
    {
        et.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (lowerBound.getText() != null && !lowerBound.getText().toString().equals("")) {
                        lowerValue = Integer.parseInt(lowerBound.getText().toString());
                        lowerBound.setText(String.valueOf(lowerValue));
                    } else {
                        lowerValue = 0;
                        lowerBound.setText("");
                    }
                    if (upperBound.getText() != null && !upperBound.getText().toString().equals("")) {
                        upperValue = Integer.parseInt(upperBound.getText().toString());
                        upperBound.setText(String.valueOf(upperValue));
                    } else {
                        upperValue = 1;
                        upperBound.setText("");

                    }

                    if (lowerValue > upperValue || lowerValue == upperValue) {
                        upperValue = lowerValue + 1;
                        upperBound.setText(String.valueOf(upperValue));
                    }

                }
            }
        });
    }
    public void openSettings(View view)
    {
        //这是一个按钮的函数，用来打开侧边栏。之后甚至还可以设置设计一个返回按钮来关闭侧边栏
        mDrawerLayout.openDrawer(mDrawerContent);
    }

    public void MenuOpenSettings(MenuItem menuItem)
    {
        //这是一个按钮的函数，用来打开侧边栏。之后甚至还可以设置设计一个返回按钮来关闭侧边栏
        mDrawerLayout.openDrawer(mDrawerContent);
    }
    public void closeSettings(View view)
    {
        //这是一个按钮的函数，用来打开侧边栏。之后甚至还可以设置设计一个返回按钮来关闭侧边栏
        mDrawerLayout.closeDrawer(mDrawerContent);
    }
}
