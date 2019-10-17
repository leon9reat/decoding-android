package com.medialink.submission5.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.medialink.submission5.Const;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT COUNT(*) FROM " + Const.TABLE_FAVORITE)
    int count();

    @Insert
    Long insert(FavoriteItem items);

    @Update
    int update(FavoriteItem... items);

    @Delete
    int delete(FavoriteItem... items);

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE)
    List<FavoriteItem> getAllFavorite();

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE + " WHERE " + Const.FIELD_FAVORITE_TYPE_ID + " = :typeId")
    List<FavoriteItem> getFavoriteByType(int typeId);

    @Query("SELECT * FROM " + Const.TABLE_FAVORITE +
            " WHERE " + Const.FIELD_FAVORITE_TYPE_ID + " = :typeId" +
            " AND " + Const.FIELD_FAVORITE_MOVIE_ID + " = :movieId")
    FavoriteItem getFavoriteByMovieId(int typeId, int movieId);

    @Query("DELETE FROM " + Const.TABLE_FAVORITE + " WHERE " + Const.FIELD_FAVORITE_ID + " = :id")
    int deleteById(long id);
}
