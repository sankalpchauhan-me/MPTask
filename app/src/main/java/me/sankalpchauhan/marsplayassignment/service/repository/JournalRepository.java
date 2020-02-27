package me.sankalpchauhan.marsplayassignment.service.repository;

import androidx.lifecycle.MutableLiveData;

import me.sankalpchauhan.marsplayassignment.service.model.JournalResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalRepository {
    private static JournalRepository newsRepository;

    public static JournalRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new JournalRepository();
        }
        return newsRepository;
    }

    private PLOSSApi newsApi;

    public JournalRepository(){
        newsApi = RetrofitService.cteateService(PLOSSApi.class);
    }

    public MutableLiveData<JournalResponse> getJournal(String source){
        MutableLiveData<JournalResponse> journalData = new MutableLiveData<>();
        newsApi.getResponse(source).enqueue(new Callback<JournalResponse>() {
            @Override
            public void onResponse(Call<JournalResponse> call,
                                   Response<JournalResponse> response) {
                if (response.isSuccessful()){
                    journalData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JournalResponse> call, Throwable t) {
                journalData.setValue(null);
            }
        });
        return journalData;
    }
}
