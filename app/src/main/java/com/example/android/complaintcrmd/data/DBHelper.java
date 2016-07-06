package com.example.android.complaintcrmd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME="pending.db";

    public DBHelper(Context context) {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String TABLE1=" CREATE TABLE IF NOT EXISTS "+ DataContract.Pending.TABLE_NAME+" ( " + DataContract.Pending.JSON+" TEXT NOT NULL )";
        final String TABLE2=" CREATE TABLE IF NOT EXISTS "+ DataContract.Completed.TABLE_NAME+" ( "+ DataContract.Completed.JSON+" TEXT NOT NULL ,"+ DataContract.Completed.REPORT+" TEXT NOT NULL )";
        sqLiteDatabase.execSQL(TABLE1);
        sqLiteDatabase.execSQL(TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+ DataContract.Pending.TABLE_NAME);
        final String TABLE1=" CREATE TABLE "+ DataContract.Pending.TABLE_NAME+" ( "+ DataContract.Pending.JSON+" TEXT NOT NULL )";
        sqLiteDatabase.execSQL(TABLE1);
    }

    public boolean insertPending(String json){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Pending.JSON, json);

        db.insert(DataContract.Pending.TABLE_NAME, null, contentValues);
        return true;

    }
    public Cursor getListPending(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( " select * from pending ", null );
        return res;
    }
    public void deletePending (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DataContract.Pending.TABLE_NAME +" WHERE json in (SELECT json FROM "+ DataContract.Pending.TABLE_NAME+" LIMIT 1 OFFSET "+id+" )" );
    }
    public void insertCompleted(String json,String abc){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Completed.JSON, json);
        contentValues.put(DataContract.Completed.REPORT,abc);
        db.insert(DataContract.Completed.TABLE_NAME,null,contentValues);
    }
    public Cursor getListCompleted(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( " select * from "+ DataContract.Completed.TABLE_NAME, null );
        return res;
    }
    public void deleteTablePending(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DataContract.Pending.TABLE_NAME +" WHERE json in (SELECT json FROM "+ DataContract.Pending.TABLE_NAME+" )" );
        //db.delete(DataContract.Pending.TABLE_NAME, null,null);
    }
}
