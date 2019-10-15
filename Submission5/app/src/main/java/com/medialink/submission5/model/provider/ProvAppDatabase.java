package com.medialink.submission5.model.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.medialink.submission5.Const;
import com.medialink.submission5.R;
import com.medialink.submission5.model.local.FavoriteItem;

@Database(entities = {FavoriteItem.class}, version = 1)
public abstract class ProvAppDatabase extends RoomDatabase {

    public abstract ProvFavDao getProvFavDao();

    private static ProvAppDatabase sDatabase;

    public static synchronized ProvAppDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room
                    .databaseBuilder(context.getApplicationContext(), ProvAppDatabase.class, Const.DATABASE_NAME)
                    .build();
            sDatabase.populateInitialData(context);
        }
        return sDatabase;
    }

    private void populateInitialData(final Context context) {
        if (getProvFavDao().count() == 0) {
            runInTransaction(new Runnable() {
                @Override
                public void run() {
                    FavoriteItem provFavItem = new FavoriteItem();
                    for (int i = 0; i < 5; i++) {
                        provFavItem.setMovieId(i);
                        provFavItem.setTypeId(Const.DETAIL_MOVIE);
                        provFavItem.setTitle("Movie Title "+i);
                        provFavItem.setReleaseDate("Release Date: 9999-99-9"+i);
                        provFavItem.setOverview(context.getString(R.string.example_overview));
                        getProvFavDao().insert(provFavItem);

                        provFavItem.setMovieId(i);
                        provFavItem.setTypeId(Const.DETAIL_TV);
                        provFavItem.setTitle("Tv Show Title "+i);
                        provFavItem.setReleaseDate("Release Date: 9999-99-9"+i);
                        provFavItem.setOverview(context.getString(R.string.example_overview));
                        getProvFavDao().insert(provFavItem);
                    }
                }
            });
        }
    }

}
