package ru.osipov.projectbehance.di;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.osipov.projectbehance.AppDelegate;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.database.BehanceDatabase;

@Module
public class AppModule {
    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    AppDelegate provideApp(){
        return mApp;
    }

    @Provides
    @Singleton
    Storage provideStorage(){

        final BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
}
