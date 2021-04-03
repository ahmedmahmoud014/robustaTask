package com.android.androidcodebase.di.module;

        import android.app.Application;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;

        import com.android.androidcodebase.data.network.RetrofitAPI;
        import com.android.androidcodebase.utils.GeneralScop;
        import com.android.androidcodebase.utils.ValidateApiResponse;
        import com.google.gson.FieldNamingPolicy;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

        import java.security.KeyManagementException;
        import java.security.NoSuchAlgorithmException;
        import java.security.cert.CertificateException;
        import java.util.Collections;
        import java.util.concurrent.TimeUnit;

        import javax.inject.Singleton;
        import javax.net.ssl.SSLContext;
        import javax.net.ssl.SSLSocketFactory;
        import javax.net.ssl.TrustManager;
        import javax.net.ssl.X509TrustManager;

        import dagger.Module;
        import dagger.Provides;
        import okhttp3.Cache;
        import okhttp3.Cookie;
        import okhttp3.OkHttpClient;
        import okhttp3.Protocol;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

        @Module
        public class NetModule {

        String mBaseUrl;

        // Constructor needs one parameter to instantiate.
        public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
        }

        // Dagger will only look for methods annotated with @Provides
        @Provides
        @GeneralScop
        // Application reference must come from AppModule.class
        SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
        }

        @Provides
        @GeneralScop
        SharedPreferences.Editor getEditor  (Application application) {
        return  providesSharedPreferences(application).edit();
        }
        @Provides
        @GeneralScop
        Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
        }


        @Provides
        @GeneralScop
        Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
        }

        @Provides
                ValidateApiResponse prepPareValidateAPI () {
                return new  ValidateApiResponse();
        }

        @Provides
         RetrofitAPI  prepareRetrofitAPI () {
          return provideRetrofit(provideGson(), provideOkHttpClient()).create(RetrofitAPI.class);
        }

        private static SSLSocketFactory getSSLSocketFactory() {
        try {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[]{};
        }
        }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        System.out.println("allCertificationnnnnnnn " + trustAllCerts.length);
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
        return null;
        }

        }

//    @Provides
//    @GeneralScop
//    ConnectionLiveData connectionLiveData(Application application){
//        return new ConnectionLiveData(application);
//    }



//    @Provides
//    @Named("non_cached")
//    @GeneralScop
//    OkHttpClient provideOkHttpClient() {
//        OkHttpClient client = new OkHttpClient();
//        return client;
//    }


        @Provides
        @GeneralScop
        OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        // protocals
        // client.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        // this is certification for ook http
        client.sslSocketFactory(getSSLSocketFactory());
        // for time out
        client .connectTimeout(1200, TimeUnit.SECONDS);
        client.readTimeout(3000, TimeUnit.SECONDS);
        client.writeTimeout(3000, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        client.cache(null);
        return client.build();
        }


        @Provides
        @GeneralScop
        Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(mBaseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build();
        return retrofit;
        }
        }
