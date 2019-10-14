package com.medialink.submission5.model.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao getFavoriteDao();
}
