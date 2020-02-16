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

import com.tsv.tsvnotedad.Items.Note;
import com.tsv.tsvnotedad.R;
import com.tsv.tsvnotedad.presenter.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity implements IMain {

    private int id;
    private Presenter presenter;

    @BindView(R.id.et_add)
    EditText editTextNote;
    @BindView(R.id.et_theme)
    EditText editTextTheme;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add: {
                saveNote();
                return true;
            }
            case R.id.action_menu_settings_2:
            case R.id.action_menu_settings_1:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.getItem(0).setIcon(R.drawable.save2);
        return true;
    }

    void toastShow(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        presenter = new Presenter(this);

        id = getIntent().getIntExtra("idItem", 0);
        id = presenter.isNewItem(id);
    }

    private void saveNote() {
        if (presenter.add(id, editTextTheme.getText().toString(), editTextNote.getText().toString())) {
            toastShow("saved");
        } else {
            toastShow("not saved");
        }
    }

    @Override
    public void showText() {
        Note note = presenter.find(id);
        getSupportActionBar().setTitle(note.getTheme());
        editTextTheme.setText(note.getTheme());
        editTextNote.setText(note.getTextNote());
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
