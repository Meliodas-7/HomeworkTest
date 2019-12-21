package com.hanyuzhou.accountingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity implements View.OnClickListener, CategoryRecyclerAdapter.OnCategoryClickListener {

    //private static String TAG = "Add";

    private EditText editText;
    private TextView amountText;
    private String userInput = "";

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter adapter;

    private String category = "日常（）";//设置文本框初始内容
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;//消费类别
    private String remark = category;

    RecordBean record = new RecordBean();//记录数据

    private boolean inEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加按键
        setContentView(R.layout.activity_add_record);
        //数字按键
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

        getSupportActionBar().setElevation(0);
//数额显示以及编辑内容
        amountText = (TextView) findViewById(R.id.textView_amount);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(remark);

        handleBackspace();//回退方法
        handleDone();//确定方法
        handleDot();//小数点
        handleTypeChange();//改变出账入账类别

        //显示选择账单种类的按钮区域
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();

        adapter.setOnCategoryClickListener(this);
//数据不为空则更新数据
        RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");
        if (record != null) {
            inEdit = true;
            this.record = record;
        }
    }

//输入小数点
    private void handleDot() {
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInput.contains(".")) {
                    userInput += ".";
                }
            }
        });
    }
//点击入账出账切换按钮
    private void handleTypeChange() {
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton button = findViewById(R.id.keyboard_type);
                //如果为出账
                if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                    button.setImageResource(R.drawable.baseline_attach_money_24);//点击后设置为入账
                } else {//如果为入账
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                    button.setImageResource(R.drawable.baseline_money_off_24);//点击后设置为出账
                }
                adapter.changeType(type);//传入种类
                category = " ";
            }
        });
    }
//点击回退
    private void handleBackspace() {
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInput.length() > 0) {//大于零的整数
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                //大于零的小数
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                updateAmountText();//更新数字显示
            }
        });
    }
//点击确定
    private void handleDone() {
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInput.equals("")) {
                    double amount = Double.valueOf(userInput);
                    record.setAmount(amount);//传入数字数据
                    //检测是入账还是出账
                    if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                        record.setType(1);
                    } else {
                        record.setType(2);
                    }
                    //record.setCategory(adapter.getSelected());
                    record.setRemark(editText.getText().toString());
//是否为编辑状态
                    if (inEdit) {
                        Icon.getInstance().databaseHelper.editRecord(record.getUuid(), record);
                    } else {
                        Icon.getInstance().databaseHelper.addRecord(record);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "金额不能为0", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        if (userInput.contains(".")) {
            if (userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2) {
                userInput += input;
            }
        } else {
            userInput += input;
        }
        updateAmountText();
//如果输入数字符合则累加
    }

//检测是否输入小数点
    private void updateAmountText() {
        //有小数点则判断小数点后几位
        if (userInput.contains(".")) {
            if (userInput.split("\\.").length == 1) {
                amountText.setText(userInput + "00");
            } else if (userInput.split("\\.")[1].length() == 1) {
                amountText.setText(userInput + "0");
            } else if (userInput.split("\\.")[1].length() == 2) {
                amountText.setText(userInput);
            }
        } else {//无小数点则补足00
            if (userInput.equals("")) {
                amountText.setText("0.00");
            } else {
                amountText.setText(userInput + ".00");
            }
        }
    }

    @Override
    public void onClikc(String category) {//编辑账单内容
        this.category = category;
        editText.setText(category);
    }
}
