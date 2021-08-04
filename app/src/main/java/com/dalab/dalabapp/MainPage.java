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
import com.dalab.dalabapp.Trains.BindPage;
import com.dalab.dalabapp.Trains.Hemostasis;
import com.dalab.dalabapp.constant.BoundValue;
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

    boolean opened=false;
    //
    public static int lowerValue = 200;
    public static int upperValue = 600;
    public static BoundValue LowValue=new BoundValue(200);
    public static BoundValue HighValue=new BoundValue(600);

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
    //全局的变量，之后可以在不同页面访问到
    public static BoundValue left_up_low_value=new BoundValue(left_up_low_def);
    public static BoundValue left_up_high_value=new BoundValue(left_up_high_def);
    public static BoundValue right_up_low_value=new BoundValue(right_up_low_def);
    public static BoundValue right_up_high_value=new BoundValue(right_up_high_def);
    public static BoundValue left_down_low_value=new BoundValue(left_down_low_def);
    public static BoundValue left_down_high_value=new BoundValue(left_down_high_def);
    public static BoundValue right_down_low_value=new BoundValue(right_down_low_def);
    public static BoundValue right_down_high_value=new BoundValue(right_down_high_def);
    public static BoundValue up_low_value=new BoundValue(up_low_def);
    public static BoundValue up_high_value=new BoundValue(up_high_def);
    public static BoundValue down_low_value=new BoundValue(down_low_def);
    public static BoundValue down_high_value=new BoundValue(down_high_def);
    public static BoundValue height_value=new BoundValue(height_def);
    public static BoundValue weight_value=new BoundValue(weight_def);

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
                        intent.setClass(MainPage.this, BlueTooth.class);
                        startActivity(intent);
                    }
                }
        );
        repeatFind();
        repaetSet();

        //创建对话框
        textTips = new AlertDialog.Builder(MainPage.this)
                .setTitle("Tips:")
//                .setMessage("1.学科限定输入格式为2—9个汉字+0或1个数字1-4，请用户自觉输入。\n" +
//                        "2.学分输入限定为小于等于10，允许输入两位小数。\n" +
//                        "3.成绩限定输入100及以内非负整数。")
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
//        left_up_Low.setText(String.valueOf(LowValue.value));
//        left_up_High.setText(String.valueOf(HighValue.value));
//        setFoucus(left_up_Low);
//        setFoucus(left_up_High);
//        setFoucuses(left_up_Low,left_up_High,left_up_Low,LowValue,HighValue);
//        setFoucuses(left_up_High,left_up_High,left_up_Low,LowValue,HighValue);

        //设备list
        mListView = findViewById(R.id.device_list);
//        mAdapter = new DeviceAdapter(mDeviceList, this);
//        mListView.setAdapter(mAdapter);
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
    private void setFoucus(final EditText et)
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
                    if (left_up_Low.getText() != null && !left_up_Low.getText().toString().equals("")) {
                        lowerValue = Integer.parseInt(left_up_Low.getText().toString());
                        left_up_Low.setText(String.valueOf(lowerValue));
                    } else {
                        lowerValue = 0;
                        left_up_Low.setText("");
                    }
                    if (left_up_High.getText() != null && !left_up_High.getText().toString().equals("")) {
                        upperValue = Integer.parseInt(left_up_High.getText().toString());
                        left_up_High.setText(String.valueOf(upperValue));
                    } else {
                        upperValue = 1;
                        left_up_High.setText("");
                    }
                    if (lowerValue > upperValue || lowerValue == upperValue) {
                        upperValue = lowerValue + 1;
                        left_up_High.setText(String.valueOf(upperValue));
                    }
                }
            }
        });
    }
    private void HWsetFoucus(final EditText et,final BoundValue hw)//height 和weight 的值设置。
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
                        hw.value = Integer.parseInt(et.getText().toString());
                        et.setText(String.valueOf(hw.value));
                    } else {
                        hw.value = 0;
                        et.setText("");
                    }
                }
            }
        });
    }
    private void setFoucuses(final EditText et,final EditText High,final EditText Low, final BoundValue lowerBound,final BoundValue upperBound)
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
                    if (Low.getText() != null && !Low.getText().toString().equals("")) {
                        lowerBound.value = Integer.parseInt(Low.getText().toString());
                        Low.setText(String.valueOf(lowerBound.value));
                    } else {
                        lowerBound.value = 0;
                        Low.setText("");
                    }
                    if (High.getText() != null && !High.getText().toString().equals("")) {
                        upperBound.value = Integer.parseInt(High.getText().toString());
                        High.setText(String.valueOf(upperBound.value));
                    } else {
                        upperBound.value = 1;
                        High.setText("");
                    }
                    if (lowerBound.value > upperBound.value || lowerBound.value  == upperBound.value) {
                        upperBound.value = lowerBound.value  + 1;
                        High.setText(String.valueOf(upperBound.value));
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
        opened=true;
    }
    public void closeSettings(View view)
    {
        //返回按钮来关闭侧边栏
        System.out.println("close!");
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
    }
    void repaetSet()
    {
        left_up_Low.setText(String.valueOf(left_up_low_value.value));
        left_up_High.setText(String.valueOf(left_up_high_value.value));
        setFoucuses(left_up_Low,left_up_High,left_up_Low,left_up_low_value,left_up_high_value);
        setFoucuses(left_up_High,left_up_High,left_up_Low,left_up_low_value,left_up_high_value);

        right_up_Low.setText(String.valueOf(right_up_low_value.value));
        right_up_High.setText(String.valueOf(right_up_high_value.value));
        setFoucuses(right_up_Low,right_up_High,right_up_Low,right_up_low_value,right_up_high_value);
        setFoucuses(right_up_High,right_up_High,right_up_Low,right_up_low_value,right_up_high_value);

        left_down_Low.setText(String.valueOf(left_down_low_value.value));
        left_down_High.setText(String.valueOf(left_down_high_value.value));
        setFoucuses(left_down_Low,left_down_High,left_down_Low,left_down_low_value,left_down_high_value);
        setFoucuses(left_down_High,left_down_High,left_down_Low,left_down_low_value,left_down_high_value);

        right_down_Low.setText(String.valueOf(right_down_low_value.value));
        right_down_High.setText(String.valueOf(right_down_high_value.value));
        setFoucuses(right_down_Low,right_down_High,right_down_Low,right_down_low_value,right_down_high_value);
        setFoucuses(right_down_High,right_down_High,right_down_Low,right_down_low_value,right_down_high_value);

        up_Low.setText(String.valueOf(up_low_value.value));
        up_High.setText(String.valueOf(up_high_value.value));
        setFoucuses(up_Low,up_High,up_Low,up_low_value,up_high_value);
        setFoucuses(up_High,up_High,up_Low,up_low_value,up_high_value);

        down_Low.setText(String.valueOf(down_low_value.value));
        down_High.setText(String.valueOf(down_high_value.value));
        setFoucuses(down_Low,down_High,down_Low,down_low_value,down_high_value);
        setFoucuses(down_High,down_High,down_Low,down_low_value,down_high_value);

        height.setText(String.valueOf(height_value.value));
        weight.setText(String.valueOf(weight_value.value));
        HWsetFoucus(height,height_value);
        HWsetFoucus(weight,weight_value);
    }

    public void setDefault(View view)//在这之前是不是还应该弹出窗口来向用户确认一下？
    {
        textTips.show();
//        if(asure)
//        {
//
//        }
//        asure=false;
    }
    private void setDef()
    {
        left_up_low_value.value=left_up_low_def;
        left_up_Low.setText(String.valueOf(left_up_low_value.value));
        left_up_high_value.value=left_up_high_def;
        left_up_High.setText(String.valueOf(left_up_high_value.value));

        left_down_low_value.value=left_down_low_def;
        left_down_Low.setText(String.valueOf(left_down_low_value.value));
        left_down_high_value.value=left_down_high_def;
        left_down_High.setText(String.valueOf(left_down_high_value.value));

        right_up_low_value.value=right_up_low_def;
        right_up_Low.setText(String.valueOf(right_up_low_value.value));
        right_up_high_value.value=right_up_high_def;
        right_up_High.setText(String.valueOf(right_up_high_value.value));

        right_down_low_value.value=right_down_low_def;
        right_down_Low.setText(String.valueOf(right_down_low_value.value));
        right_down_high_value.value=right_down_high_def;
        right_down_High.setText(String.valueOf(right_down_high_value.value));

        up_low_value.value=up_low_def;
        up_Low.setText(String.valueOf(up_low_value.value));
        up_high_value.value=up_high_def;
        up_High.setText(String.valueOf(up_high_value.value));

        down_low_value.value=down_low_def;
        down_Low.setText(String.valueOf(down_low_value.value));
        down_high_value.value=down_high_def;
        down_High.setText(String.valueOf(down_high_value.value));

        height_value.value=height_def;
        height.setText(String.valueOf(height_value.value));
        weight_value.value=weight_def;
        weight.setText(String.valueOf(weight_value.value));
    }
}
