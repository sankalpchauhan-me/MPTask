package me.sankalpchauhan.marsplayassignment.service.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import me.sankalpchauhan.marsplayassignment.service.model.Journal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalRepository {
    private static JournalRepository journalRepository;

    public static JournalRepository getInstance() {
        if (journalRepository == null) {
            journalRepository = new JournalRepository();
        }
        return journalRepository;
    }

    private PLOSSApi journalApi;

    public JournalRepository() {
        journalApi = RetrofitService.cteateService(PLOSSApi.class);
        Log.e("Test", journalApi.getResponse("title:DNA").request().url().toString());
    }

    public MutableLiveData<Journal> getJournal(String source) {
        MutableLiveData<Journal> journalData = new MutableLiveData<>();
        journalApi.getResponse(source).enqueue(new Callback<Journal>() {
            @Override
            public void onResponse(Call<Journal> call,
                                   Response<Journal> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    journalData.setValue(response.body());
                } else {
                    Log.d("API Request", "Request failed with error" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Journal> call, Throwable t) {
                Log.e("Test", t.toString());
                journalData.setValue(null);
            }
        });
        return journalData;
    }
}