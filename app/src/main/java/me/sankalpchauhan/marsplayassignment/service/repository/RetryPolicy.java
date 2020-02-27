package me.sankalpchauhan.marsplayassignment.service.repository;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *     Retry Policy and Logging Request
 */

class RetryPolicy {
    static OkHttpClient okClient() {
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
                //Retry Policy
                .addInterceptor(new ErrorInterceptor())
                //Logging
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }

    //Retry Policy
    public static class ErrorInterceptor implements Interceptor {
        Response response;
        int tryCount = 0;
        int maxLimit = 3;
        int waitThreshold = 5000;


        @Override
        public Response intercept(Chain chain) throws IOException {

            // String language =  cacheManager.readPreference(PreferenceKeys.LANGUAGE_CODE);
            Request request = chain.request();
            response = sendReqeust(chain, request);
            while (response == null && tryCount < maxLimit) {
                Log.d("intercept", "Request failed - " + tryCount);
                tryCount++;
                try {
                    Thread.sleep(waitThreshold); // force wait the network thread for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = sendReqeust(chain, request);
            }
            return chain.proceed(request);
        }

        private Response sendReqeust(Chain chain, Request request) {
            try {
                response = chain.proceed(request);
                if (!response.isSuccessful())
                    return null;
                else
                    return response;
            } catch (IOException e) {
                return null;
            }
        }
    }

    private static Cookie createNonPersistentCookie() {
        return new Cookie.Builder()
                .domain("api.plos.org")
                .path("/")
                .name("token")
                .value("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6ODM2ODgwNTkwM30.sQisPnG7Iorhdp-5i6wLpu4GZtedwaMbXtf2y6kcnEc")
                .httpOnly()
                .secure()
                .build();
    }


}
