package me.sankalpchauhan.marsplayassignment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.sankalpchauhan.marsplayassignment.service.model.JournalResponse;
import me.sankalpchauhan.marsplayassignment.service.repository.JournalRepository;

public class JournalViewModel extends ViewModel {
    private MutableLiveData<JournalResponse> mutableLiveData;
    private JournalRepository journalRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        journalRepository = journalRepository.getInstance();
        mutableLiveData = journalRepository.getJournal("title:DNA");

    }

    public LiveData<JournalResponse> getNewsRepository() {
        return mutableLiveData;
    }
}
