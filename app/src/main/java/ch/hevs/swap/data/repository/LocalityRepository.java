package ch.hevs.swap.data.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import ch.hevs.swap.data.AppDatabase;
import ch.hevs.swap.data.entity.LocalityEntity;

public class LocalityRepository {
    private static LocalityRepository sInstance;

    private final AppDatabase mDatabase;

    private LocalityRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static LocalityRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (LocalityRepository.class) {
                if (sInstance == null) {
                    sInstance = new LocalityRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<LocalityEntity> getLocality(final Long id) {
        return mDatabase.localityDao().getById(id);
    }
    public LiveData<List<LocalityEntity>> getLocalities() {
        return mDatabase.localityDao().getAll();
    }

    public void insert(final LocalityEntity locality) {
        mDatabase.localityDao().insert(locality);
    }

    public void update(final LocalityEntity locality) {
        mDatabase.localityDao().update(locality);
    }

    public void delete(final LocalityEntity locality) {
        mDatabase.localityDao().delete(locality);
    }
}
