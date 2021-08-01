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
    LinearLayout textLayout;
    EditText left_up_Low;
    EditText left_up_High;
    EditText right_up_Low;
    EditText right_up_High;
    EditText left_down_Low;
    EditText left_down_High;
    EditText right_down_Low;
    EditText right_down_High;
    EditText up_Low;
    EditText up_High;
    EditText down_Low;
    EditText down_High;
    EditText height, weight;
    public static int lowerValue = 200;
    public static int upperValue = 600;

    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_page);

        mDrawerLayout=findViewById(R.id.drawer_layout);
//        findViewById(R.id.right_drawer).getParent().requestDisallowInterceptTouchEvent(true);//这样就不会自动退出了?
        mDrawerContent=findViewById(R.id.right_drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);//一开始是打开的，不能够自动关闭，但是能够通过代码的方式关闭
        mDrawerLayout.closeDrawer(mDrawerContent);
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
        repeatFind();
        left_up_Low.setText(String.valueOf(lowerValue));
        left_up_High.setText(String.valueOf(upperValue));
//        setFoucus(left_up_Low);
//        setFoucus(left_up_High);
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
//    private void setFoucus(final EditText et)
//    {
//        et.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    System.out.println("get Foucus");
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    if (lowerBound.getText() != null && !lowerBound.getText().toString().equals("")) {
//                        lowerValue = Integer.parseInt(lowerBound.getText().toString());
//                        lowerBound.setText(String.valueOf(lowerValue));
//                    } else {
//                        lowerValue = 0;
//                        lowerBound.setText("");
//                    }
//                    if (upperBound.getText() != null && !upperBound.getText().toString().equals("")) {
//                        upperValue = Integer.parseInt(upperBound.getText().toString());
//                        upperBound.setText(String.valueOf(upperValue));
//                    } else {
//                        upperValue = 1;
//                        upperBound.setText("");
//                    }
//                    if (lowerValue > upperValue || lowerValue == upperValue) {
//                        upperValue = lowerValue + 1;
//                        upperBound.setText(String.valueOf(upperValue));
//                    }
//
//                }
//            }
//        });
//    }
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
        System.out.println("close!");
        mDrawerLayout.closeDrawer(mDrawerContent);
    }


    //解决点击自动关闭的问题
    // tools
    void repeatFind()
    {
        left_up_Low = findViewById(R.id.left_up_Low);
        left_up_High = findViewById(R.id.left_up_High);
        right_up_Low = findViewById(R.id.right_up_Low);
        right_up_High = findViewById(R.id.right_up_High);
        left_down_Low = findViewById(R.id.left_down_Low);
        left_down_High = findViewById(R.id.left_down_High);
        right_down_Low = findViewById(R.id.right_down_Low);
        right_down_High = findViewById(R.id.right_down_High);
        up_Low = findViewById(R.id.up_Low);
        up_High = findViewById(R.id.up_High);
        down_Low = findViewById(R.id.down_Low);
        down_High = findViewById(R.id.down_High);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
    }
    void repaetSet()
    {

    }

}
