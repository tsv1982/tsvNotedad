package com.tsv.tsvnotedad.model;

import com.tsv.tsvnotedad.Items.Note;
import com.tsv.tsvnotedad.presenter.IPresenter;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model instance;

    private IPresenter iPresenter;

    private List<Note> items;

    private Model() {
        items = new ArrayList<>();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public void setActivity(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    public boolean addItem(Note note) {
        try {
            if (findItem(note.getId()) == null) {
                items.add(note);
            } else {
                return changeItem(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        startPresenter();
        return true;
    }

    public boolean deleteItem(int i) {
        try {
            items.remove(findItem(i));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        startPresenter();
        return true;
    }

    private boolean changeItem(Note note) {
        try {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == note.getId()) {
                    items.set(i, note);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Note findItem(int i) {
        for (Note n : items) {
            if (n.getId() == i) {
                return n;
            }
        }
        return null;
    }

    public List<Note> getItems() {
        return items;
    }

    private void startPresenter() {
        iPresenter.startView();
    }

}



