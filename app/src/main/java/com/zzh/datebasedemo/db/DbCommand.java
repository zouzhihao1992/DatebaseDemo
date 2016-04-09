package com.zzh.datebasedemo.db;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zzh on 16/4/8.
 */
public abstract class DbCommand<T> {
    private static ExecutorService sDbEngine = Executors.newSingleThreadExecutor();
    private final static Handler sUihandler = new Handler(Looper.getMainLooper());

    public final void execute(){
        sDbEngine.execute(new Runnable() {
            @Override
            public void run() {
                try{
                   postResult(doInBackground());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void postResult(final T result){
        sUihandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

    protected abstract  T doInBackground();

    protected void onPostExecute(T result){

    }

}
