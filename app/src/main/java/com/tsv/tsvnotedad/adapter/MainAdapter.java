package com.tsv.tsvnotedad.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.Items.Note;
import com.tsv.tsvnotedad.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends ArrayAdapter<Note> implements View.OnCreateContextMenuListener {

    private LayoutInflater inflater;
    private int layout;
    private List<Note> items;

    public MainAdapter(Context context, int resource, List<Note> items) {
        super(context, resource, items);
        this.items = items;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        com.tsv.tsvnotedad.adapter.MainAdapter.ViewHolder holder;
        if (view != null) {
            holder = (com.tsv.tsvnotedad.adapter.MainAdapter.ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(this.layout, parent, false);
            holder = new com.tsv.tsvnotedad.adapter.MainAdapter.ViewHolder(view);
            view.setTag(holder);
        }

        holder.textView_theme.setText(items.get(position).getTheme());
        holder.textView_date.setText(items.get(position).getDate());
        holder.textViewId.setText(String.valueOf(items.get(position).getId()));

        holder.textView_note.setText(showNoteMain(items.get(position).getTextNote()));

        view.setOnCreateContextMenuListener(this);

        return view;
    }

    private String showNoteMain(String s) {
        String[] words = s.split(" ");
        String str = "";
        if (words.length > 4) {
            str = words[0] + " " + words[1] + "..." ;
            str += words[words.length / 2] + "...";
            str += words[words.length - 1];
        }else {
            str = s;
        }
        return str;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        TextView textId = v.findViewById(R.id.id_note);
        int id = Integer.parseInt(textId.getText().toString());
        menu.add(0, Util.IDM_OPEN, id, " OPEN");
        menu.add(0, Util.IDM_DELETE, id, "DELETE");
    }

    static class ViewHolder {

        @BindView(R.id.tv_theme)
        TextView textView_theme;
        @BindView(R.id.tv_text_note)
        TextView textView_note;
        @BindView(R.id.tv_date)
        TextView textView_date;
        @BindView(R.id.id_note)
        TextView textViewId;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}



