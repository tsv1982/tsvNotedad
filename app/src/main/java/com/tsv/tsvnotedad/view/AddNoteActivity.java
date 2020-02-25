package com.tsv.tsvnotedad.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.model.INote;
import com.tsv.tsvnotedad.model.Note;
import com.tsv.tsvnotedad.model.XmlNotesModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    private int id;
    private XmlNotesModel xmlNotesModel;

    @BindView(R.id.et_add)
    EditText editTextNote;
    @BindView(R.id.et_theme)
    EditText editTextTheme;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add: {
                if (editTextNote.getText().toString().equals("")) {
                    toastShow("empty note");
                } else {
                    if (editTextTheme.getText().toString().equals("")) {
                        editTextTheme.setText(R.string.no_subject);
                    }
                    saveNote();
                }
                return true;
            }
            case R.id.action_menu_delete: {
                Log.i("MyLog", String.valueOf(id));
                if (xmlNotesModel.removeNote(id)) {
                    toastShow("deleted note");
                    finish();
                } else {
                    toastShow("no notes to delete");
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.getItem(0).setIcon(R.drawable.save);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        xmlNotesModel = XmlNotesModel.getInstance(this.getFilesDir().getPath());

        getNote(id = getIntent().getIntExtra("idItem", 0));
    }

    void toastShow(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void saveNote() {
        if (xmlNotesModel.addNote(new Note(id, editTextTheme.getText().toString(),
                editTextNote.getText().toString(), new Date()))) {
            toastShow("saved");
        } else {
            toastShow("not saved");
        }
    }

    private void getNote(int id) {
        INote note = xmlNotesModel.findNote(id);
        if (note != null) {
            editTextTheme.setText(note.getTheme());
            editTextNote.setText(note.getTextNote());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
