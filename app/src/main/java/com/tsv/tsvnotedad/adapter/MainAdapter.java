package com.tsv.tsvnotedad.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.model.INote;
import com.tsv.tsvnotedad.view.AddNoteActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends ArrayAdapter<INote> implements View.OnCreateContextMenuListener {

    private static final int IDM_OPEN = 101;
    private static final int IDM_DELETE = 102;

    private LayoutInflater inflater;
    private int layout;
    private List<INote> items;

    public MainAdapter(Context context, int resource, List<INote> items) {
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

        INote item = items.get(position);

        holder.textViewId.setText(String.valueOf(item.getId()));
        holder.textView_title.setText(item.getTitle());
        holder.textView_note.setText(showNoteMain(item.getText()));
        holder.textView_date.setText(String.valueOf(item.getTime()));

        holder.btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddNoteActivity.class);
            intent.putExtra("idItem", item.getId());
            v.getContext().startActivity(intent);
        });

        view.setOnCreateContextMenuListener(this);
        return view;
    }

    private String showNoteMain(String s) {
        String[] words = s.split(" ");
        String str = "";
        if (words.length > 4) {
            str = words[0] + " " + words[1] + "...";
            str += words[words.length / 2] + "...";
            str += words[words.length - 1];
        } else {
            str = s;
        }
        return str;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        TextView textId = v.findViewById(R.id.id_note);
        int id = Integer.parseInt(textId.getText().toString());
        menu.add(0, IDM_OPEN, id, " OPEN");
        menu.add(0, IDM_DELETE, id, "DELETE");
    }

    static class ViewHolder {

        @BindView(R.id.tv_title)
        TextView textView_title;
        @BindView(R.id.tv_text_note)
        TextView textView_note;
        @BindView(R.id.tv_date)
        TextView textView_date;
        @BindView(R.id.id_note)
        TextView textViewId;
        @BindView(R.id.btn_open)
        Button btnOpen;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}



