package com.starstore.hugo.victor.starstore.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Victor Hugo on 18/01/2018.
 */

@Database(entities = {CartDB.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    public static final String DB_NAME = "starStoreDB";
    private static DataBase INSTANCE;

    public abstract CartDAO cartDao();

    public static DataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBase.class, DB_NAME).build();
        }

        return INSTANCE;
    }
}