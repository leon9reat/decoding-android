package com.medialink.submission5.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(FavoriteItem... items);

    @Update
    void update(FavoriteItem... items);

    @Delete
    void delete(FavoriteItem... items);

    @Query("SELECT * FROM favorite")
    List<FavoriteItem> getAllFavorite();

    @Query("SELECT * FROM favorite WHERE typeId = :typeId")
    List<FavoriteItem> getFavoriteByType(int typeId);

    @Query("SELECT * FROM favorite WHERE movieId = :movieId")
    FavoriteItem getFavoriteByMovieId(int movieId);
}
