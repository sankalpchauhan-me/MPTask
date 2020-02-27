package me.sankalpchauhan.marsplayassignment.service.repository;

import me.sankalpchauhan.marsplayassignment.service.model.Journal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PLOSSApi {
    @GET("search")
    Call<Journal> getResponse(@Query("q") String source);
}

