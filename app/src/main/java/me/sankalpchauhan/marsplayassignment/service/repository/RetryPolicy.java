package me.sankalpchauhan.marsplayassignment.service.repository;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.sankalpchauhan.marsplayassignment.MyApp;
import me.sankalpchauhan.marsplayassignment.util.Utility;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static me.sankalpchauhan.marsplayassignment.MyApp.getContext;

/**
 * Retry Policy and Request Logging
 */

class RetryPolicy {

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utility.isConnected(getContext())) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };


    static OkHttpClient okClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File httpCacheDirectory = new File(getContext().getCacheDir(), "offlineCache");
        //10 MB
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cache(cache)
                //Retry Policy
                .addInterceptor(new ErrorInterceptor())
                //Logging
                .addInterceptor(loggingInterceptor)
                //Caching
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .build();

        return client;
    }

    //Retry Policy
    public static class ErrorInterceptor implements Interceptor {
        Response response;
        int tryCount = 0;
        int maxLimit = 3;
        // wait for 5 second before retrying
        int waitThreshold = 5000;

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            response = sendReqeust(chain, request);
            while (response == null && tryCount < maxLimit) {
                Log.d("intercept", "Request failed - " + tryCount);
                tryCount++;
                try {
                    Thread.sleep(waitThreshold);
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



}
