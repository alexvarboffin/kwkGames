package com.walhalla.luckypunch.ui;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.NoteListRowBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rubikstudio.library.model.LuckyItem;


public class ProxyAdapter extends RecyclerView.Adapter<ProxyAdapter.MyViewHolder> {

    private final Context context;
    public final List<LuckyItem> notesList;

    public void setSelected(LuckyItem wrapper) {
        for (LuckyItem c : notesList) {
            if (c._id == (wrapper._id)) {
                c.active = wrapper.active;
            } else {
                c.active = false;
            }
        }
        notifyDataSetChanged();
    }

    public void swap(List<LuckyItem> o) {
        this.notesList.clear();
        this.notesList.addAll(o);
        this.notifyDataSetChanged();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {

        private final NoteListRowBinding bind;

        MyViewHolder(NoteListRowBinding binding) {
            super(binding.getRoot());
            this.bind = binding;
        }

        public void bind(LuckyItem note) {

            //secondaryText
            if (note.secondaryText.isEmpty()) {
                bind.host.setVisibility(View.GONE);
            } else {
                bind.host.setVisibility(View.VISIBLE);
            }
            //        if (note.isActive()) {
//            // Displaying dot from HTML character code
//            holder.dot.setText(Html.fromHtml("&#8226;"));
//            holder.dot.setTextColor(Color.parseColor("#00E5FF"));
//        } else {
//            // Displaying dot from HTML character code
//            holder.dot.setText(Html.fromHtml("&#8226;"));
//            holder.dot.setTextColor(Color.RED);
//        }

            if (note.active) {
                bind.dot.setBackgroundColor(Color.RED);
            } else {
                // Displaying dot from HTML character code
                bind.dot.setBackgroundColor(Color.BLACK);
            }

            // Formatting and displaying timestamp
            if (note.topText.isEmpty()) {
                bind.description.setVisibility(View.GONE);
            } else {
                bind.description.setText(note.topText);
            }
            if (note.icon > 0) {
                bind.icon.setImageResource(note.icon);
            } else {
                bind.icon.setImageResource(R.drawable.ic_item1);
            }
        }
    }

    public ProxyAdapter(Context context, List<LuckyItem> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteListRowBinding mBind =
                NoteListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(mBind);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LuckyItem note = notesList.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d", Locale.getDefault());
            return fmtOut.format(date);
        } catch (ParseException ignored) {

        }

        return "";
    }
}