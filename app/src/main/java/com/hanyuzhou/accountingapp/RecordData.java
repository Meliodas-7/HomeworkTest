package com.hanyuzhou.accountingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class RecordData extends SQLiteOpenHelper {

    private String TAG ="RecordData";

    public static final String DB_NAME = "Record";

    private static final String CREATE_RECORD_DB = "create table Record ("
        + "id integer primary key autoincrement, "
        + "uuid text, "
        + "type integer, "
        + "category, "
        + "remark text, "
        + "amount double, "
        + "time integer, "
        + "date date )";

    public RecordData(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//创建数据列表
    public void addRecord(RecordBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getType());
        values.put("category",bean.getCategory());
        values.put("remark",bean.getRemark());
        values.put("amount",bean.getAmount());
        values.put("date",bean.getDate());
        values.put("time",bean.getTimeStamp());
        db.insert(DB_NAME,null,values);
        values.clear();
        Log.d(TAG,bean.getUuid()+"added");
    }
//删除
    public void  removeRecord(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }
//编辑
    public void editRecord(String uuid,RecordBean record){
        removeRecord(uuid);
        record.setUuid(uuid);
        addRecord(record);
    }
//记录存储数据
    public LinkedList<RecordBean> readRecords(String dateStr){
        LinkedList<RecordBean> records = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Record where date = ? order by time asc",new String[]{dateStr});
        if (cursor.moveToFirst()){
            do{
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));//账单标签
                int type = cursor.getInt(cursor.getColumnIndex("type"));//类别
                String category = cursor.getString(cursor.getColumnIndex("category"));//种类
                String remark = cursor.getString(cursor.getColumnIndex("remark"));//便签
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));//数额
                String date = cursor.getString(cursor.getColumnIndex("date"));//日期
                long timeStamp = cursor.getLong(cursor.getColumnIndex("time"));//时间

                RecordBean record = new RecordBean();
                record.setUuid(uuid);
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setDate(date);
                record.setTimeStamp(timeStamp);

                records.add(record);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public LinkedList<String> getAvaliableDate(){

        LinkedList<String> dates = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Record order by date asc",new String[]{});
        if (cursor.moveToFirst()){
            do{
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (!dates.contains(date)){
                    dates.add(date);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }
}
