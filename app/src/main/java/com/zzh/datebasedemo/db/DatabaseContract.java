package com.zzh.datebasedemo.db;

import android.provider.BaseColumns;

/**
 * Created by zzh on 16/4/8.
 */
public final class DatabaseContract {
    public DatabaseContract(){

    }

    public static abstract class User implements BaseColumns{
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_ACCOUNT = "Account";
        public static final String COLUMN_NAME_PASSWORD = "Password";
    }

    public static abstract class Weibo implements BaseColumns{
        public static final String TABLE_NAME = "Weibo";
        public static final String COLUMN_NAME_WEIBOID = "WeiboId";
        public static final String COLUMN_NAME_CONTENT = "Content";
    }

    public static abstract class DistributeWeibo implements BaseColumns{
        public static final String TABLE_NAME = "DistributeWeibo";
        public static final String COLUMN_NAME_USERID = "UserId";
        public static final String COLUMN_NAME_WEIBOID = "WeiboId";
    }

}
