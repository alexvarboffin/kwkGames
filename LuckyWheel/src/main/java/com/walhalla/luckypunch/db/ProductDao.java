package com.walhalla.luckypunch.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import rubikstudio.library.model.LuckyItem;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM LuckyItem")
    List<LuckyItem> getAll();

    @Query("SELECT * FROM LuckyItem WHERE _id = :id")
    LuckyItem getById(long id);

//    @Insert
//    void insert(LuckyItem proxy);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LuckyItem item);

    // Insert multiple items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(LuckyItem... user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<LuckyItem> user);


    @Update
    void update(LuckyItem item);

    @Delete
    void delete(LuckyItem product);

    @Query("SELECT COUNT(/*is_checked*/) FROM LuckyItem")/*  WHEREis_checked = 1*/
    int getLuckyItemCount();
}
