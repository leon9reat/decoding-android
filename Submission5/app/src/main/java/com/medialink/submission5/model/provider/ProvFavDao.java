package com.medialink.submission5.model.provider;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.medialink.submission5.Const;
import com.medialink.submission5.model.local.FavoriteItem;

@Dao
public interface ProvFavDao {

    @Query("SELECT COUNT(*) FROM " + Const.TABLE_FAVORITE)
    int count();

    @Insert
    Long insert(FavoriteItem provFavItem);

    @Update
    int update(FavoriteItem provFavItem);

    @Delete
    int delete(FavoriteItem provFavItem);

    @Query("DELETE FROM " + Const.TABLE_FAVORITE + " WHERE " + Const.FIELD_FAVORITE_ID + " = :id")
    int deleteById(long id);

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE)
    Cursor getAllFavorite();

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE + " WHERE " + Const.FIELD_FAVORITE_TYPE_ID + " = :typeId")
    Cursor getFavoriteByType(int typeId);

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE +
            " WHERE " + Const.FIELD_FAVORITE_TYPE_ID + " = :typeId" +
            " AND " + Const.FIELD_FAVORITE_MOVIE_ID + " = :movieId")
    Cursor getFavoriteByMovieId(int typeId, int movieId);
}
