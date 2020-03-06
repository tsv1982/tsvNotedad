package com.tsv.tsvnotedad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.adapter.MainAdapter;
import com.tsv.tsvnotedad.model.XmlNotesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int IDM_OPEN = 101;
    private static final int IDM_DELETE = 102;

    private MainAdapter mainAdapter;
    private XmlNotesModel xmlNotesModel;

    @BindView(R.id.lv_main)
    ListView listView;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_OPEN: {
                startActivity(new Intent(this, AddNoteActivity.class).putExtra("idItem", item.getOrder()));
            }
            case IDM_DELETE: {
                if (xmlNotesModel.removeNote(item.getOrder())) {
                    showToast("item deleted");
                    mainAdapter.notifyDataSetChanged();
                } else {
                    showToast("item not deleted");
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add: {
                startActivity(new Intent(this, AddNoteActivity.class));
                break;
            }
            case R.id.action_menu_delete: {
                if (xmlNotesModel.removeAllNote()) {
                    showToast("all items deleted");
                    mainAdapter.notifyDataSetChanged();
                } else {
                    showToast("not remotely");
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.getItem(1).setTitle("delete all items");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        xmlNotesModel = XmlNotesModel.getInstance(this.getFilesDir().getPath());

        mainAdapter = new MainAdapter(this, R.layout.list_main, xmlNotesModel.notes());
        listView.setAdapter(mainAdapter);
    }

    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainAdapter.notifyDataSetChanged();
    }


}
