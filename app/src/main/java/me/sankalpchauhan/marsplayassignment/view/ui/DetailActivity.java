package me.sankalpchauhan.marsplayassignment.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import me.sankalpchauhan.marsplayassignment.R;
import me.sankalpchauhan.marsplayassignment.service.model.Doc;

public class DetailActivity extends AppCompatActivity {
    TextView tvArticleType, tvTitle, tvAuthor, tvPublish, tvId, tvEissn, tvAbstract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Journal Entry");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        Doc entry = (Doc) intent.getSerializableExtra("Entry");
        List<String> list = entry.getAuthorDisplay();
        StringBuilder listString = new StringBuilder();
        for (String s : list) {
            listString.append(s).append(", ");
        }

        tvArticleType = findViewById(R.id.article_type);
        tvTitle = findViewById(R.id.title);
        tvAuthor = findViewById(R.id.author);
        tvPublish = findViewById(R.id.tvPublish);
        tvId = findViewById(R.id.tvId);
        tvEissn = findViewById(R.id.eissn);
        tvAbstract = findViewById(R.id.tvAbstract);

        tvArticleType.setText(String.format("%s | %s", entry.getArticleType(), entry.getJournal()));
        tvTitle.setText(entry.getTitleDisplay());
        tvAuthor.setText(String.format("By %s", listString));
        tvPublish.setText(String.format("%s %s", entry.getJournal(), entry.getPublicationDate()));
        tvId.setText(String.format("ID: %s", entry.getId()));
        tvEissn.setText(String.format("EISSN: %s", entry.getEissn()));
        tvAbstract.setText(entry.getAbstract().get(0));
        if(entry.getArticleType().equals("Correction")){
            tvAbstract.setText(String.format("Correction on %s", entry.getTitleDisplay()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
