package me.sankalpchauhan.marsplayassignment.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import me.sankalpchauhan.marsplayassignment.util.Utility;
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
    ConstraintLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.plos_journal));
        }
        rvHeadline = findViewById(R.id.rvEntry);
        progressBar = findViewById(R.id.progress_circular);
        retryRequest = findViewById(R.id.oops);
        parentView = findViewById(R.id.parent);
        if(!Utility.isConnected(this)){
            Utility.setSnackBar(parentView, getResources().getString(R.string.no_internet));
        }
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
                Toast.makeText(this, getResources().getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                retryRequest.setVisibility(View.VISIBLE);
            }
        });

        retryRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: retry request with clone()
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

