package me.sankalpchauhan.marsplayassignment.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.sankalpchauhan.marsplayassignment.R;
import me.sankalpchauhan.marsplayassignment.service.model.Doc;
import me.sankalpchauhan.marsplayassignment.service.model.Journal;
import me.sankalpchauhan.marsplayassignment.view.ui.DetailActivity;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
    int mExpandedPosition =-1;
    Context context;
    ArrayList<Doc> entries;

    public JournalAdapter(Context context, ArrayList<Doc> entries) {
        this.context = context;
        this.entries = entries;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_item, parent, false);
        return new  JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        List<String> list = entries.get(position).getAuthorDisplay();
        StringBuilder listString= new StringBuilder();
        for (String s : list)
        {
            listString.append(s).append(", ");
        }
        holder.tvTitle.setText(entries.get(position).getTitleDisplay());
        holder.tvAuthor.setText(String.format("By %s", listString));
        //Date date = entries.get(position).getPublicationDate()
//        String string = entries.get(position).getPublicationDate();
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try {
//            date = inputFormat.parse(string);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String formattedDate = outputFormat.format(date);
        holder.tvDetails.setText(String.format("%s| %s:%s | Score: %s", entries.get(position).getJournal(), entries.get(position).getPublicationDate(), entries.get(position).getEissn(), entries.get(position).getScore()));

        holder.tvAbstract.setText(entries.get(position).getAbstract().get(0));
        final boolean isExpanded = position==mExpandedPosition;
        if(entries.get(position).getArticleType().equals("Correction")){
            holder.tvAbstract.setVisibility(View.GONE);
        }else{
            holder.tvAbstract.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        }
        holder.btnReadMore.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });
        holder.btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("Entry", entries.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAuthor, tvDetails, tvAbstract;
        Button btnReadMore;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvDetails = itemView.findViewById(R.id.details);
            tvAbstract = itemView.findViewById(R.id.tvAbstract);
            btnReadMore = itemView.findViewById(R.id.read_more);

        }
    }
}
