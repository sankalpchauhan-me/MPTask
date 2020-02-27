package me.sankalpchauhan.marsplayassignment.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView retryRequest;
    String retryMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("PLOS Journal");
        }
        rvHeadline = findViewById(R.id.rvEntry);
        progressBar = findViewById(R.id.progress_circular);
        retryRequest = findViewById(R.id.oops);


        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        journalViewModel.init();
        journalViewModel.getJournalRepository().observe(this, journalResponse -> {
            progressBar.setVisibility(View.GONE);
            if (journalResponse != null) {
                List<Doc> journalEntries = journalResponse.getResponse().getDocs();
                articleArrayList.addAll(journalEntries);
                journalAdapter.notifyDataSetChanged();
                retryRequest.setVisibility(View.GONE);
            } else {
                Log.d(getLocalClassName(), "Response is null");
                Toast.makeText(this, "Something Went Wrong...", Toast.LENGTH_LONG).show();
                retryRequest.setVisibility(View.VISIBLE);
            }
        });

        retryRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finishAffinity();
                finish();
                Log.d(getLocalClassName(), retryMessage);
                startActivity(intent);
            }
        });

        setupRecyclerView();

    }

    private void setupRecyclerView() {
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

