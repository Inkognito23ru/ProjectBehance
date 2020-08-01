package ru.osipov.projectbehance;

import android.app.Application;
import androidx.room.Room;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.database.BehanceDatabase;

public class AppDelegate extends Application {

    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        final BehanceDatabase database = Room.databaseBuilder(this, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        mStorage = new Storage(database.getBehanceDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
