package com.dalab.dalabapp;

import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dalab.dalabapp.Adapter.ListAdapter;
import com.dalab.dalabapp.Animation.Animation;
import com.dalab.dalabapp.BlueTooth.BlueTooth;
import com.dalab.dalabapp.BlueTooth.DeviceAdapter;
import com.dalab.dalabapp.Bluno.Bluno_demo;
import com.dalab.dalabapp.Trains.BindPage;
import com.dalab.dalabapp.Trains.Hemostasis;
import com.dalab.dalabapp.constant.BoundValue;
import com.dalab.dalabapp.constant.Global;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    EditText up_Low;// 绷带的数据
    EditText up_High;
    EditText down_Low;
    EditText down_High;
    EditText height, weight;
    EditText bindTime, hoeoTime;

    boolean opened=false;
    //
    public static int lowerValue = 200;
    public static int upperValue = 600;

    //先设定默认值
    static int left_up_low_def=200;//改成static只是为了能够在下面全局变量赋值的时候能够输入进去，这个值之后本身也不会给用户去修改。
    static int left_up_high_def=600;
    static int right_up_low_def=200;
    static int right_up_high_def=600;
    static int left_down_low_def=200;
    static int left_down_high_def=600;
    static int right_down_low_def=200;
    static int right_down_high_def=600;
    static int up_low_def=10;
    static int up_high_def=35;
    static int down_low_def=10;
    static int down_high_def=35;

    static int height_def=175;//cm
    static int weight_def=65;//kg


    ListView mylist;
    AlertDialog textTips;
//    boolean asure=false;

    //蓝牙相关
    private ListView mListView;
    private DeviceAdapter mAdapter;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();

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
        mylist.setAdapter(new ListAdapter(MainPage.this));//这里的listAdapter是自己写的类
        mylist.setOnItemClickListener(
                new MyOnItemClickListener()
        );
        FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(opened)
                        {
                            return;//如果侧边栏是打开状态，那么就提前跳出，不会响应按钮
                        }
                        Intent intent = new Intent();
//                        intent.setClass(MainPage.this, BlueTooth.class);
                        intent.setClass(MainPage.this, Bluno_demo.class);
                        startActivity(intent);
                    }
                }
        );
        repeatFind();
        repaetSet();
        setDef();
        //创建对话框
        textTips = new AlertDialog.Builder(MainPage.this)
                .setTitle("Tips:")
                .setMessage("您确认要恢复至默认设置吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainPage.this, "Asure",Toast.LENGTH_SHORT).show();
//                        asure=true;//确认
                        setDef();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainPage.this, "Cancel",Toast.LENGTH_SHORT).show();
//                        asure=false;//确认
                    }
                })
                .create();


        //设备list
        mListView = findViewById(R.id.device_list);
        mListView.setAdapter(new ListAdapter(MainPage.this));//这里的listAdapter是自己写的类
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
            if(opened)
            {
                return;//如果侧边栏是打开状态，那么就提前跳出，不会响应按钮
            }
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

    private void HWsetFoucus(final EditText et,final int type)//height 和weight 的值设置。
    {
        et.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    System.out.println("get Foucus");
                } else {
                    // 此处为失去焦点时的处理内容
                    if (et.getText() != null && !et.getText().toString().equals("")) {
                        int tmp = Integer.parseInt(et.getText().toString());
                        setValue(tmp, type);
                        et.setText(String.valueOf(tmp));
                    } else {
                        setValue(0,type);
                        et.setText("");
                    }
                }
            }
        });
    }
    private void setFoucuses(final EditText et, final EditText High, final EditText Low, final int type)
    {
        et.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int tmpLow = 0, tmpHigh = 1;
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    System.out.println("get Foucus");
                } else {
                    // 此处为失去焦点时的处理内容
                    if (Low.getText() != null && !Low.getText().toString().equals("")) {
                        tmpLow = Integer.parseInt(Low.getText().toString());
                        Low.setText(String.valueOf(tmpLow));
                        setValue(tmpLow, type);

                    } else {
                        setValue(0, type);
                        Low.setText("");
                    }

                    if (High.getText() != null && !High.getText().toString().equals("")) {
                        tmpHigh = Integer.parseInt(High.getText().toString());
                        setValue(tmpHigh, type + 1);
                        High.setText(String.valueOf(tmpHigh));
                    } else {
                        setValue(1, type + 1);
                        High.setText("");
                    }
                    if (tmpHigh <= tmpLow) {
                        tmpHigh = tmpLow  + 1;
                        High.setText(String.valueOf(tmpHigh));
                        setValue(tmpHigh, type + 1);
                    }
                }
            }
        });
    }
    // setValue
    void setValue(int value, int type)
    {
        switch (type)
        {
            case 1: Global.global.left_up_low_value = value;
                break;
            case 2: Global.global.left_up_high_value = value;
                break;
            case 3: Global.global.right_up_low_value = value;
                break;
            case 4: Global.global.right_up_high_value = value;
                break;
            case 5: Global.global.left_down_low_value = value;
                break;
            case 6: Global.global.left_down_high_value = value;
                break;
            case 7: Global.global.right_down_low_value = value;
                break;
            case 8: Global.global.right_down_high_value = value;
                break;
            case 9: Global.global.up_low_value = value;
                break;
            case 10: Global.global.up_high_value = value;
                break;
            case 11: Global.global.down_low_value = value;
                break;
            case 12: Global.global.down_high_value = value;
                break;
            case 13: Global.global.height_value = value;
                break;
            case 14: Global.global.weight_value = value;
                break;
            case 15:Global.global.bindTime = value;
                break;
            case 16:Global.global.hoeoTime = value;
                break;
        }
    }


    public void MenuOpenSettings(MenuItem menuItem)
    {
        //这是一个按钮的函数，用来打开侧边栏。之后甚至还可以设置设计一个返回按钮来关闭侧边栏
        mDrawerLayout.openDrawer(mDrawerContent);
        opened=true;
    }
    public void closeSettings(View view)
    {
        //返回按钮来关闭侧边栏
        mDrawerLayout.closeDrawer(mDrawerContent);
        opened=false;
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
        bindTime = findViewById(R.id.bindTime);
        hoeoTime = findViewById(R.id.hoeoTime);
    }
    void repaetSet()
    {
        left_up_Low.setText(String.valueOf(Global.global.left_up_low_value));
        left_up_High.setText(String.valueOf(Global.global.left_up_high_value));
        setFoucuses(left_up_Low,left_up_High,left_up_Low,1);
        setFoucuses(left_up_High,left_up_High,left_up_Low,1);

        right_up_Low.setText(String.valueOf(Global.global.right_up_low_value));
        right_up_High.setText(String.valueOf(Global.global.right_up_high_value));
        setFoucuses(right_up_Low,right_up_High,right_up_Low,3);
        setFoucuses(right_up_High,right_up_High,right_up_Low,3);

        left_down_Low.setText(String.valueOf(Global.global.left_down_low_value));
        left_down_High.setText(String.valueOf(Global.global.left_down_high_value));
        setFoucuses(left_down_Low,left_down_High,left_down_Low,5);
        setFoucuses(left_down_High,left_down_High,left_down_Low,5);

        right_down_Low.setText(String.valueOf(Global.global.right_down_low_value));
        right_down_High.setText(String.valueOf(Global.global.right_down_high_value));
        setFoucuses(right_down_Low,right_down_High,right_down_Low,7);
        setFoucuses(right_down_High,right_down_High,right_down_Low,7);

        up_Low.setText(String.valueOf(Global.global.up_low_value));
        up_High.setText(String.valueOf(Global.global.up_high_value));
        setFoucuses(up_Low,up_High,up_Low,9);
        setFoucuses(up_High,up_High,up_Low,9);

        down_Low.setText(String.valueOf(Global.global.down_low_value));
        down_High.setText(String.valueOf(Global.global.down_high_value));
        setFoucuses(down_Low,down_High,down_Low,11);
        setFoucuses(down_High,down_High,down_Low,11);

        height.setText(String.valueOf(Global.global.height_value));
        weight.setText(String.valueOf(Global.global.weight_value));
        HWsetFoucus(height,13);
        HWsetFoucus(weight,14);
        HWsetFoucus(bindTime, 15);
        HWsetFoucus(hoeoTime, 16);
    }

    public void setDefault(View view)//在这之前是不是还应该弹出窗口来向用户确认一下？
    {
        textTips.show();
    }
    private void setDef()
    {
        Global.global.left_up_low_value = left_up_low_def;
        left_up_Low.setText(String.valueOf(left_up_low_def));
        Global.global.left_up_high_value=left_up_high_def;
        left_up_High.setText(String.valueOf(left_up_high_def));

        Global.global.left_down_low_value=left_down_low_def;
        left_down_Low.setText(String.valueOf(left_down_low_def));
        Global.global.left_down_high_value=left_down_high_def;
        left_down_High.setText(String.valueOf(left_down_high_def));

        Global.global.right_up_low_value=right_up_low_def;
        right_up_Low.setText(String.valueOf(right_up_low_def));
        Global.global.right_up_high_value=right_up_high_def;
        right_up_High.setText(String.valueOf(right_up_high_def));

        Global.global.right_down_low_value=right_down_low_def;
        right_down_Low.setText(String.valueOf(right_down_low_def));
        Global.global.right_down_high_value=right_down_high_def;
        right_down_High.setText(String.valueOf(right_down_high_def));

        Global.global.up_low_value=up_low_def;
        up_Low.setText(String.valueOf(up_low_def));
        Global.global.up_high_value=up_high_def;
        up_High.setText(String.valueOf(up_high_def));

        Global.global.down_low_value=down_low_def;
        down_Low.setText(String.valueOf(down_low_def));
        Global.global.down_high_value=down_high_def;
        down_High.setText(String.valueOf(down_high_def));

        Global.global.height_value=height_def;
        height.setText(String.valueOf(height_def));
        Global.global.weight_value=weight_def;
        weight.setText(String.valueOf(height_def));
    }
}