package com.example.windy.wind.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by windy on 2017/12/19.
 */

public class DatabaseCreator {
    private volatile static DatabaseCreator INSTANCE;
    private AppDatabase mDatabase;

    private AtomicBoolean isCreated = new AtomicBoolean(false);

    public static final String DB_NAME = "app_database";

    private DatabaseCreator(){}

    public static DatabaseCreator getInstance(){
        if (INSTANCE == null){
            synchronized (DatabaseCreator.class){
                if (INSTANCE == null)
                    INSTANCE = new DatabaseCreator();
            }
        }
        return INSTANCE;
    }

    public void createDatabase(Context context){
        if (isCreated.get()){
            Log.v("Database", "Database Has Been Created");
            return;
        }

        new AsyncTask<Context, Void, Void>(){
            @Override
            protected Void doInBackground(Context... contexts) {
                Log.v("Database", "Starting bg job at " + Thread.currentThread().getName());

                Context context = contexts[0].getApplicationContext();

                mDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.v("Database", "Create database successfully");
                isCreated.set(true);
            }
        }.execute(context.getApplicationContext());
    }

    public boolean isCreated(){
        return isCreated.get();
    }

    public AppDatabase getDatabase(){
        return mDatabase;
    }
}
