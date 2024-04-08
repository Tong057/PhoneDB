package com.example.phonedb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository mRepository;
    private final LiveData<List<Phone>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhoneRepository(application);
        mAllPhones = mRepository.getAllPhones();
    }

    LiveData<List<Phone>> getAllPhones() {
        return mAllPhones;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteByPosition(int index) {
        mRepository.deleteByPosition(index);
    }

    public void update(Phone phone) {
        mRepository.update(phone);
    }

    public void insert(Phone phone) {
        mRepository.insert(phone);
    }

    public void delete(Phone phone) {
        mRepository.delete(phone);
    }
}
