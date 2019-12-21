package com.hanyuzhou.accountingapp;

import java.io.Serializable;
import java.util.UUID;

public class RecordBean implements Serializable{

    //public static String TAG = "RecordBean";

    public enum RecordType{
        RECORD_TYPE_EXPENSE,RECORD_TYPE_INCOME
    }

    private double amount;
    private RecordType type;
    private String category;
    private String remark;
    private String date;

    private long timeStamp;
    private String uuid;

    public RecordBean(){
        uuid = UUID.randomUUID().toString();
        timeStamp = System.currentTimeMillis();
        date = DateShow.getFormattedDate();
    }

   /* public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        RecordBean.TAG = TAG;
    }*/

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        if (this.type == RecordType.RECORD_TYPE_EXPENSE){
            return 1;
        }else {
            return 2;
        }
    }

    public void setType(int type) {
        if (type==1){
            this.type = RecordType.RECORD_TYPE_EXPENSE;
        }
        else {
            this.type = RecordType.RECORD_TYPE_INCOME;
        }
    }

    //账单种类
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    //便签
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    //日期
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    //时间
    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    //每条账单的标签
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
