package com.tsv.tsvnotedad.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.model.INote;
import com.tsv.tsvnotedad.model.XmlNote;
import com.tsv.tsvnotedad.model.XmlNotesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    private XmlNotesModel xmlNotesModel;
    private INote note;

    @BindView(R.id.et_add)
    EditText editTextNote;
    @BindView(R.id.et_title)
    EditText editTextTitle;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add: {
                saveNote();
                break;
            }
            case R.id.action_menu_delete: {
                deleteNote();
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

        getNote(getIntent().getIntExtra("idItem", 0));
    }

    private void toastShow(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void saveNote() {
        if (editTextNote.getText().toString().equals("")) {
            toastShow("empty note");
        } else {
            if (editTextTitle.getText().toString().equals("")) {
                editTextTitle.setText(R.string.no_subject);
            }
            note = new XmlNote(note.getId(), editTextTitle.getText().toString(),
                    editTextNote.getText().toString());
            if (xmlNotesModel.addNote(note)) {
                toastShow("saved");
            } else {
                toastShow("not saved");
            }
        }
    }

    private void deleteNote() {
        if (xmlNotesModel.removeNote(note.getId())) {
            toastShow("deleted note");
            finish();
        } else {
            toastShow("no notes to delete");
        }
    }

    private void getNote(int id) {
        note = xmlNotesModel.findNote(id);
        if (note != null) {
            editTextTitle.setText(note.getTitle());
            editTextNote.setText(note.getText());
        }else {
            note = new XmlNote();
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
