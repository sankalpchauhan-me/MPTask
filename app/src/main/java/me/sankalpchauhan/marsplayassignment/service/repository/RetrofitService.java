package me.sankalpchauhan.marsplayassignment.service.repository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.plos.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okClient())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient okClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        final ArrayList<Cookie> oneCookie = new ArrayList<>(1);
                        oneCookie.add(createNonPersistentCookie());
                        return oneCookie;
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }
    public static Cookie createNonPersistentCookie() {
        return new Cookie.Builder()
                .domain("services.intellify.in")
                .path("/")
                .name("token")
                .value("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6ODM2ODgwNTkwM30.sQisPnG7Iorhdp-5i6wLpu4GZtedwaMbXtf2y6kcnEc")
                .httpOnly()
                .secure()
                .build();
    }
}
