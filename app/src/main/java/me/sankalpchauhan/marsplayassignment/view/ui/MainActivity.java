package me.sankalpchauhan.marsplayassignment.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import me.sankalpchauhan.marsplayassignment.R;
import me.sankalpchauhan.marsplayassignment.service.model.Doc;
import me.sankalpchauhan.marsplayassignment.view.adapter.JournalAdapter;
import me.sankalpchauhan.marsplayassignment.viewmodel.JournalViewModel;

public class MainActivity extends AppCompatActivity {
    ArrayList<Doc> articleArrayList = new ArrayList<>();
    JournalAdapter journalAdapter;
    RecyclerView rvHeadline;
    JournalViewModel journalViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvHeadline = findViewById(R.id.rvEntry);
        progressBar = findViewById(R.id.progress_circular);


        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        journalViewModel.init();
        journalViewModel.getJournalRepository().observe(this, journalResponse -> {
            progressBar.setVisibility(View.GONE);
            if(journalResponse.getResponse().getDocs()!=null) {
                List<Doc> journalEntries = journalResponse.getResponse().getDocs();
                articleArrayList.addAll(journalEntries);
                journalAdapter.notifyDataSetChanged();

            }
            else{
                Log.e(getLocalClassName(), "Response is null");
            }
        });
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        Log.e(getLocalClassName(), "I am here");

        if (journalAdapter == null) {
            journalAdapter = new JournalAdapter(MainActivity.this, articleArrayList);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(journalAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            journalAdapter.notifyDataSetChanged();
        }
    }

}

