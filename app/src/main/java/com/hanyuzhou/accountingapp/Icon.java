package com.hanyuzhou.accountingapp;

import android.content.Context;

import java.util.LinkedList;

public class Icon {

   // private static final String TAG = "Icon";

    private static Icon instance;
    private static int i=0;

    public RecordData databaseHelper;
    //private Context context;
    public MainActivity mainActivity;

    public LinkedList<CategoryBean> costRes = new LinkedList<>();//出账图标
    public LinkedList<CategoryBean> earnRes = new LinkedList<>();//入账图标

   private static int [] costIconRes = {
            R.drawable.icon_general_white,
            R.drawable.icon_food_white,
            R.drawable.icon_drinking_white,
            R.drawable.icon_groceries_white,
            R.drawable.icon_shopping_white,
            R.drawable.icon_entertain_white,
            R.drawable.icon_medical_white,
            R.drawable.icon_movie_white,
    };
   /* private static int [] costIconResBlack = {
            R.drawable.icon_general,
            R.drawable.icon_food,
            R.drawable.icon_drinking,
            R.drawable.icon_groceries,
            R.drawable.icon_shopping,
            R.drawable.icon_entertain,
            R.drawable.icon_medical,
    };*/
    private static String[] costTitle = {"日常", "食物", "饮料","杂货", "购物" ,"娱乐", "医疗","电影"};

/*    private static int[] earnIconRes = {
            R.drawable.icon_general,
            R.drawable.icon_reimburse,
            R.drawable.icon_salary,
            R.drawable.icon_redpocket,
            R.drawable.icon_parttime,
            R.drawable.icon_bonus,
            R.drawable.icon_investment
};*/


    private static int[] earnIconResBlack = {

            R.drawable.icon_general_white,
            R.drawable.icon_reimburse_white,
            R.drawable.icon_salary_white,
            R.drawable.icon_redpocket_white,
            R.drawable.icon_parttime_white,
            R.drawable.icon_bonus_white,
            R.drawable.icon_investment_white,
            R.drawable.icon_ticket_white
    };

    private static String[] earnTitle = {"综合", "赔偿", "薪水","红包","兼职", "奖金","投资","彩票"};

/*    public Context getContext() {
        return context;
    }*/
//遍历列表将图标与标题绑定
    public void setContext(Context context) {
       // this.context = context;
        databaseHelper = new RecordData(context, RecordData.DB_NAME,null,1);

        for (int i=0;i<costTitle.length;i++){
            CategoryBean res = new CategoryBean();
            res.title = costTitle[i];
          /*  res.resBlack = costIconResBlack[i];*/
            res.resBlack = costIconRes[i];
            costRes.add(res);
        }

        for (int i=0;i<earnTitle.length;i++){
            CategoryBean res = new CategoryBean();
            res.title = earnTitle[i];
            res.resBlack = earnIconResBlack[i];
           /* res.resWhite = earnIconRes[i];*/
            earnRes.add(res);
        }

    }
    //调用函数显示图标和标题
    public static Icon getInstance(){

        if ( instance ==null&&i==0){
            instance = new Icon();
            i++;
        }
        return instance;
    }

/*    public int getResourceIcon(String category){
        for (CategoryBean res :
                costRes) {
            if (res.title.equals(category)){
                return res.resWhite;
            }
        }

        for (CategoryBean res :
                earnRes) {
            if (res.title.equals(category)){
                return res.resWhite;
            }
        }

        return costRes.get(0).resWhite;
    }*/
}
