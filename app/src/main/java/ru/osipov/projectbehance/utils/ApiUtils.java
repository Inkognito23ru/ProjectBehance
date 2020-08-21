package ru.osipov.projectbehance.utils;

import com.google.gson.Gson;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.data.api.ApiKeyInterceptor;
import ru.osipov.projectbehance.data.api.BehanceApi;

public class ApiUtils {

    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );
}
