package me.sankalpchauhan.marsplayassignment.service.repository;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Retry Policy and Request Logging
 */

class RetryPolicy {
    static OkHttpClient okClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient().newBuilder()
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
