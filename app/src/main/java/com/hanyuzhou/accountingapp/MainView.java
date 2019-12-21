package com.hanyuzhou.accountingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class MainView extends FragmentPagerAdapter {

    LinkedList<MainFragment> fragments = new LinkedList<>();

    LinkedList<String> dates = new LinkedList<>();

    public MainView(FragmentManager fm) {
        super(fm);
        initFragments();
    }
//获取日期数据
    private void initFragments(){
        dates = Icon.getInstance().databaseHelper.getAvaliableDate();

        if (!dates.contains(DateShow.getFormattedDate())){
            dates.addLast(DateShow.getFormattedDate());
        }

        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }

    public void reload(){
        for (MainFragment fragment :
                fragments) {
            fragment.reload();
        }
    }

    public int getLatsIndex(){
        return fragments.size()-1;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

/*    public String getDateStr(int index){
        return dates.get(index);
    }*/

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
    public int getTotalIncome(int index){
        return fragments.get(index).getTotalIncome();
    }
}
