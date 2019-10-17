package com.medialink.submission5.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.medialink.submission5.Const;
import com.medialink.submission5.model.provider.ProvAppDatabase;

@Database(entities = {FavoriteItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao getFavoriteDao();
    private static AppDatabase sDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, Const.DATABASE_NAME)
                    .build();
        }
        return sDatabase;
    }
}
