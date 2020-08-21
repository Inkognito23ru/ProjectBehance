package ru.osipov.projectbehance;

import android.app.Application;
import ru.osipov.projectbehance.di.AppComponent;
import ru.osipov.projectbehance.di.AppModule;
import ru.osipov.projectbehance.di.DaggerAppComponent;
import ru.osipov.projectbehance.di.NetworkModule;

public class AppDelegate extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getAppComponent(){
        return sAppComponent;
    }
}
