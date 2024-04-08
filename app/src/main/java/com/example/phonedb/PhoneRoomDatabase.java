package com.example.phonedb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract PhoneDAO phoneDAO();

    private static volatile PhoneRoomDatabase INSTANCE;

    static PhoneRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhoneRoomDatabase.class, "phones")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Phone>> getAllPhones() {
        return phoneDAO().getAlphabetizedPhones();
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PhoneDAO dao = INSTANCE.phoneDAO();
                // Create and add Phones to the database
                dao.insert(new Phone("Google", "Pixel 4", 14.0, "https://www.pixel.google.com"));
                dao.insert(new Phone("OnePlus", "8Pro", 13.0, "https://www.oneplus.com"));
                dao.insert(new Phone("Samsung", "Galaxy S21", 12.0, "https://www.samsung.com"));
                dao.insert(new Phone("Apple", "Iphone 12", 0, "https://www.apple.com"));
            });
        }
    };

}
