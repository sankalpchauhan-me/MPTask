package me.sankalpchauhan.marsplayassignment.service.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.plos.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
