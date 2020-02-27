package me.sankalpchauhan.marsplayassignment.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        Intent intent = getIntent();
        Doc entry = (Doc) intent.getSerializableExtra("Entry");
        List<String> list = entry.getAuthorDisplay();
        StringBuilder listString= new StringBuilder();
        for (String s : list)
        {
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


    }
}
