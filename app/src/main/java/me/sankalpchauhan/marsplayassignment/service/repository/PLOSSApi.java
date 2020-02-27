package me.sankalpchauhan.marsplayassignment.service.repository;

import me.sankalpchauhan.marsplayassignment.service.model.JournalResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PLOSSApi {
    @GET("search?q=title:DNA")
    Call<JournalResponse> getResponse(@Query("title") String source);
}

