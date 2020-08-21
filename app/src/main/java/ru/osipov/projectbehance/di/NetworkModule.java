package ru.osipov.projectbehance.di;

import androidx.room.Room;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.osipov.projectbehance.AppDelegate;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.api.ApiKeyInterceptor;
import ru.osipov.projectbehance.data.api.BehanceApi;
import ru.osipov.projectbehance.data.database.BehanceDao;
import ru.osipov.projectbehance.data.database.BehanceDatabase;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideClient() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new ApiKeyInterceptor());
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    // need for interceptors
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }

    @Provides
    @Singleton
    BehanceApi provideApiService(Retrofit retrofit) {
        return retrofit.create(BehanceApi.class);
    }
}
