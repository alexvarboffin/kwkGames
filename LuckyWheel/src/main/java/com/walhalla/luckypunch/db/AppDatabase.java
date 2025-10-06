package com.walhalla.luckypunch.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.walhalla.luckypunch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import rubikstudio.library.model.LuckyItem;


@Database(entities = {LuckyItem.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(() ->
                    {
                        ProductDao dao = INSTANCE.proxyDao();

                        if (!flag) {
                            flag = true;
                            dao.insert(populateData());
                        }
                    }

            );
        }
    };

    public abstract ProductDao proxyDao();

    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "data")
//                .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }


    static boolean flag = false;


    private static List<LuckyItem> populateData() {

        List<LuckyItem> data = new ArrayList<>();
        LuckyItem item = new LuckyItem("Item1", "Item1");
        item.icon = R.raw.ic_item1;
        item.color = 0xffFFF3E0;
        item._id = 1L;
        data.add(item);

        LuckyItem luckyItem2 = new LuckyItem("Item2", "Item2");
        luckyItem2.icon = R.raw.ic_item2;
        luckyItem2.color = 0xffFFE0B2;
        luckyItem2._id = 2L;
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem("Item3", "Item3");
        luckyItem3.icon = R.raw.ic_item3;
        luckyItem3.color = 0xffFFCC80;
        luckyItem3._id = 3L;
        data.add(luckyItem3);

        return data;
    }

}