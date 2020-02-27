package me.sankalpchauhan.marsplayassignment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.sankalpchauhan.marsplayassignment.R;
import me.sankalpchauhan.marsplayassignment.service.model.Doc;
import me.sankalpchauhan.marsplayassignment.service.model.Journal;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

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
        holder.tvAuthor.setText(listString);
        holder.tvDetails.setText(entries.get(position).getJournal());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAuthor, tvDetails;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvDetails = itemView.findViewById(R.id.details);

        }
    }
}
