package com.tsv.tsvnotedad.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.tsv.tsvnotedad.Items.Note;
import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.Util;
import com.tsv.tsvnotedad.adapter.MainAdapter;
import com.tsv.tsvnotedad.presenter.Presenter;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMain {

    private Presenter presenter;
    private boolean aBoolean = true;

    @BindView(R.id.lv_main)
    ListView listView;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Util.IDM_OPEN: {
                Intent intent = new Intent(this, AddNoteActivity.class);
                intent.putExtra("idItem", item.getOrder());
                startActivity(intent);
                break;
            }
            case Util.IDM_DELETE: {
                if (presenter.delete(item.getOrder())) {
                    showToast("item deleted");
                } else {
                    showToast("item not deleted");
                }
                break;
            }
        }
        return true;
    }

    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add: {
                Intent intent = new Intent(this, AddNoteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.action_menu_settings_2:
            case R.id.action_menu_settings_1:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void showText() {
        MainAdapter mainAdapter = new MainAdapter(this, R.layout.list_main, presenter.getList());
        listView.setAdapter(mainAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter = new Presenter(this);
        getXmlResources();
        showText();
    }

    public void getXmlResources() {
        if (aBoolean) {
            try {
                XmlPullParser parser = getResources().getXml(R.xml.note);

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("note")) {
                        presenter.add(Integer.parseInt(String.valueOf(parser.getAttributeValue(1))),
                                parser.getAttributeValue(3),
                                parser.getAttributeValue(2));
                    }
                    parser.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        aBoolean = false;
    }
}
