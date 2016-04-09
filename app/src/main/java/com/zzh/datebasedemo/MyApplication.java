package com.zzh.datebasedemo;

import android.app.Application;

import com.zzh.datebasedemo.db.DatabaseHelper;

/**
 * Created by zzh on 16/4/8.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.init(this);
    }
}
