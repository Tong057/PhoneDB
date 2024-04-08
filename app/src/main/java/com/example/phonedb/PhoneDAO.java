package com.example.phonedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface PhoneDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);

    @Query("DELETE FROM phones")
    void deleteAll();

    @Query("SELECT * FROM phones ORDER BY model ASC")
    LiveData<List<Phone>> getAlphabetizedPhones();

    @Query("DELETE FROM phones WHERE id = :position")
    void deleteByPosition(int position);

}
