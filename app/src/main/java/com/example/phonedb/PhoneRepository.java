package com.example.phonedb;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDAO mPhoneDAO;
    private LiveData<List<Phone>> mAllPhones;

    PhoneRepository(Application application) {
        PhoneRoomDatabase phoneRoomDatabase = PhoneRoomDatabase.getDatabase(application);

        mPhoneDAO = phoneRoomDatabase.phoneDAO();
        mAllPhones = phoneRoomDatabase.getAllPhones();
    }

    LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDAO.deleteAll();
        });

    }

    void insert(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDAO.insert(phone);
        });
    }

    void update(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDAO.update(phone);
        });
    }

    public void delete(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDAO.delete(phone);
        });
    }

    void deleteByPosition(int index) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhoneDAO.deleteByPosition(index);
        });
    }

}
