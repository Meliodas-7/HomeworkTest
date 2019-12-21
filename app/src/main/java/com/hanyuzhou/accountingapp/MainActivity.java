package com.hanyuzhou.accountingapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG ="MainActivity";

    private ViewPager viewPager;
    private MainView pagerAdapter;
    private TickerView amountText;
    private TickerView amountText2;
    private TextView dateText;
    private int currentPagerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Icon.getInstance().setContext(getApplicationContext());
        Icon.getInstance().mainActivity = this;
        getSupportActionBar().setElevation(0);
//显示账单主界面
        amountText = (TickerView)findViewById(R.id.amount_text);
        amountText2 = (TickerView)findViewById(R.id.amount_text2);
        amountText.setCharacterLists(TickerUtils.provideNumberList());
        amountText2.setCharacterLists(TickerUtils.provideNumberList());
       /* dateText=(TextView) findViewById(R.id.date_text);*/

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MainView(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(pagerAdapter.getLatsIndex());

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击切换到记账界面
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivityForResult(intent,1);
            }
        });
        updateHeader();
    }
//显示页面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.reload();
        updateHeader();
    }
//滚动页面
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
//切换界面
    @Override
    public void onPageSelected(int position) {
        Log.d(TAG,""+pagerAdapter.getTotalCost(position));
        currentPagerPosition = position;
        updateHeader();
    }
//更新金额
    public void updateHeader(){
        String amount = String.valueOf(pagerAdapter.getTotalCost(currentPagerPosition));
        amountText.setText("支出："+amount);
        String amount2 = String.valueOf(pagerAdapter.getTotalIncome(currentPagerPosition));
        amountText2.setText("收入："+amount2);
        /*String date = pagerAdapter.getDateStr(currentPagerPosition);*/
 /*       dateText.setText(DateShow.getWeekDay(date));*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
