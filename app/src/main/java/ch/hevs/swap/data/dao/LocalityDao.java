package ch.hevs.swap.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import ch.hevs.swap.data.entity.LocalityEntity;

@Dao
public interface LocalityDao {

    @Query("SELECT * FROM localities WHERE idLocality = :id")
    LiveData<LocalityEntity> getById(Long id);

    @Query("SELECT * FROM localities")
    LiveData<List<LocalityEntity>> getAll();

    @Insert
    long insert(LocalityEntity locality);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LocalityEntity> locality);

    @Update
    void update(LocalityEntity locality);

    @Delete
    void delete(LocalityEntity locality);

    @Query("DELETE FROM localities")
    void deleteAll();
}
