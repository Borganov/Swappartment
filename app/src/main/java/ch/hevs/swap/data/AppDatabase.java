package ch.hevs.swap.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import ch.hevs.swap.data.dao.LocalityDao;
import ch.hevs.swap.data.entity.LocalityEntity;

@Database(entities = {LocalityEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase sInstance;

    private static final String DATABASE_NAME = "epermit-db";

    public abstract LocalityDao localityDao();


    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
//                    sInstance = buildDatabase(context.getApplicationContext());
//                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
