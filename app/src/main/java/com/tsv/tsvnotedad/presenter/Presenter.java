package com.tsv.tsvnotedad.presenter;

import com.tsv.tsvnotedad.Items.Note;
import com.tsv.tsvnotedad.Util;
import com.tsv.tsvnotedad.model.Model;
import com.tsv.tsvnotedad.view.IMain;

import java.util.List;
import java.util.Random;

public class Presenter implements IPresenter {

    private Model model;
    private IMain iMain;

    public Presenter(IMain iMain) {
        this.iMain = iMain;
        model = Model.getInstance();
        model.setActivity(this);

    }

    public boolean add(int id, String theme, String text) {
        if (!text.equals("")) {

            Note note = new Note();

            if (theme.equals("")) {
                note.setTheme("no subject");
            } else {
                note.setTheme(theme);
            }
            note.setId(id);
            note.setTextNote(text);
            note.setDate(Util.getDate());

            return model.addItem(note);

        } else {
            return false;
        }
    }

    public boolean delete(int id) {
        return model.deleteItem(id);
    }

    public List<Note> getList() {
        return model.getItems();
    }

    public int isNewItem(int id) {
        if (id != 0) {
           startView();
        } else {
            id = new Random().nextInt(99999);
        }
        return id ;
    }

    public Note find(int id) {
        return model.findItem(id);
    }

    @Override
    public void startView() {
        iMain.showText();
    }

}
