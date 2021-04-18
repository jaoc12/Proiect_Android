package ro.example.proiect.database;

import android.app.Application;

import androidx.room.Room;

public class AppApplication extends Application {

    private static AppApplication mInstance;
    private static AppDatabase mAppDatabase;

    @Override
    public void onCreate(){
        super.onCreate();

        mInstance = this;
        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-database").build();
    }

    public static AppApplication getInstance(){
        return mInstance;
    }

    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }
}
