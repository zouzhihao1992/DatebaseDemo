package com.zzh.datebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zzh.datebasedemo.bean.User;
import com.zzh.datebasedemo.db.DatabaseHelper;
import com.zzh.datebasedemo.db.DbCommand;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DbCommand<List<User>>() {
            @Override
            protected List<User> doInBackground() {
                //List<User> list = new  ArrayList<User>();
                for (int i = 0;i < 100 ;i++){
                    DatabaseHelper.getInstance().saveUser(new User(i+"",i+1+""));
                }
                return DatabaseHelper.getInstance().queryUser();
            }

            @Override
            protected void onPostExecute(List<User> result) {
                super.onPostExecute(result);
                for (User user : result){
                    Log.d("zzh","user account = " + user.account);
                }
            }
        }.execute();
    }
}
