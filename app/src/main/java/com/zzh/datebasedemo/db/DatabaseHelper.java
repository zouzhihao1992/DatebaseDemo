package com.zzh.datebasedemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zzh.datebasedemo.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzh on 16/4/8.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Weibo.db";
    private static final int DB_VERSION = 2;
    private SQLiteDatabase mDatabase;
    static DatabaseHelper sDatabasehelper = null;

    public static final String CREATE_USER_TABLE_SQL =
            "create table " + DatabaseContract.User.TABLE_NAME
                    + "(" + DatabaseContract.User.COLUMN_NAME_ACCOUNT + " text primary key not null unique,"
                    + DatabaseContract.User.COLUMN_NAME_PASSWORD + " text"
                    + ")";

    public static final String CREATE_WEIBO_TABLE_SQL =
            "create table " + DatabaseContract.Weibo.TABLE_NAME
                    + "(" + DatabaseContract.Weibo.COLUMN_NAME_WEIBOID + " text primary key not null unique,"
                    + DatabaseContract.Weibo.COLUMN_NAME_CONTENT + " text"
                    + ")";
    public static final String CREATE_DISTRIBUTE_TABLE_SQL =
            "create table " + DatabaseContract.DistributeWeibo.TABLE_NAME + " ("
                    + DatabaseContract.DistributeWeibo.COLUMN_NAME_WEIBOID + " text,"
                    + DatabaseContract.DistributeWeibo.COLUMN_NAME_USERID + " text,"
                    + "primary key " + "(" + DatabaseContract.DistributeWeibo.COLUMN_NAME_WEIBOID
                    + "," + DatabaseContract.DistributeWeibo.COLUMN_NAME_USERID + ")"
                    + ")";


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mDatabase = getWritableDatabase();
    }

    public static void init(Context context) {

        if (sDatabasehelper == null) {
            sDatabasehelper = new DatabaseHelper(context);
            Log.d("zzh", "database init");
        }
    }

    public static DatabaseHelper getInstance() {
        if (sDatabasehelper == null) {
            throw new NullPointerException("sDatabasehelper is null");
        }
        return sDatabasehelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_SQL);
        db.execSQL(CREATE_WEIBO_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_DISTRIBUTE_TABLE_SQL);
            default:
                break;
        }

    }

    //根据业务需求对外暴露一些数据库操作的接口
    //此处以User表为例，做一些实现，其他表类似

    public void saveUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.User.COLUMN_NAME_ACCOUNT,user.account);
        contentValues.put(DatabaseContract.User.COLUMN_NAME_PASSWORD,user.password);
        mDatabase.insert(DatabaseContract.User.TABLE_NAME,null,contentValues);
    }

    public void deleteUser(User user){
        mDatabase.delete(DatabaseContract.User.TABLE_NAME,
                DatabaseContract.User.COLUMN_NAME_ACCOUNT + " = ?",new String[]{user.account});
    }

    public void updateUser(User userAfter){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseContract.User.COLUMN_NAME_PASSWORD,userAfter.password);
        mDatabase.update(DatabaseContract.User.TABLE_NAME,contentValue,
                DatabaseContract.User.COLUMN_NAME_ACCOUNT + " = ?",new String[]{userAfter.account});

    }

    public List<User> queryUser(){
        List<User> result = new ArrayList<>();
       Cursor cursor =  mDatabase.query(DatabaseContract.User.TABLE_NAME,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                String account = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.COLUMN_NAME_ACCOUNT));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.COLUMN_NAME_PASSWORD));
                User user = new User(account,password);
                result.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return result;

    }


}
