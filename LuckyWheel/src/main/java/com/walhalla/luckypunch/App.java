package com.walhalla.luckypunch;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.walhalla.luckypunch.db.AppDatabase;

/*
*
* keytool -list -v -keystore D:/walhalla/sign/keystore.jks -alias luckypunch -storepass @!sfuQ123zpc -keypass @!sfuQ123zpc
* */
public class App extends MultiDexApplication {

    private final boolean tracker = false;

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
//        database = Room.databaseBuilder(this,
//                AppDatabase.class,
//                "proxy")
//                .allowMainThreadQueries()
//                //.fallbackToDestructiveMigration()
//                .addMigrations(new Migration(9, 10) {
//                    @Override
//                    public void migrate(@NonNull SupportSQLiteDatabase database) {
////                        database.execSQL(
//////                                "ALTER TABLE users "
//////                                +"ADD COLUMN address TEXT"
////
////                        "DELETE FROM proxy"
////
////                        );
//                    }
//                })
//                .addCallback(new RoomDatabase.Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//
////                        Executors.newSingleThreadExecutor().execute(new Runnable() {
////                            @Override
////                            public void run() {
//                        ProxyDao proxyDao = getDatabase().proxyDao();
//                        proxyDao.insert(
//                                DataEntity.populateData()
//                        );
//
////                            }
////                        });
//
//                    }
//                })
//                .build();

        database = AppDatabase.getInstance(this);

//        if(tracker){
//            Fabric.with(this, new Crashlytics());
//        }
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
