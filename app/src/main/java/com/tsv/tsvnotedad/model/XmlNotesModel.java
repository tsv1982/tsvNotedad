package com.tsv.tsvnotedad.model;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlNotesModel implements INotesModel {

    private static XmlNotesModel instance;

    private List<INote> notes;
    private String url;

    private XmlNotesModel(String url) {
        notes = new ArrayList<>();
        this.url = url;
        loadFromXml();
    }

    public static XmlNotesModel getInstance(String url) {
        if (instance == null) {
            instance = new XmlNotesModel(url);
        }
        return instance;
    }

    private boolean loadFromXml() {
        Serializer serializer = new Persister();
        File file = new File(url, "notes.xml");
        try {
            XmlNotes xmlNotes = serializer.read(XmlNotes.class, file);
            notes = xmlNotes.getNotes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void saveToXml() {
        new Thread(new Runnable() {
            public void run() {
                Serializer serializer = new Persister();
                File file = new File(url, "notes.xml");

                try {
                    serializer.write(new XmlNotes(notes), file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean addNote(INote note) {
        if (note.getId() == 0) {

            int id;

            if (notes.size() == 0) {
                id = 1;
            } else {
                id = notes.size();
            }

            while (findNote(id) != null) {
                id++;
            }

            note.setId(id);
            notes.add(note);
            saveToXml();
            return true;

        } else {

            if (findNote(note.getId()) != null) {
                return changeNote(note);
            } else {
                return false;
            }

        }
    }

    @Override
    public boolean removeNote(int id) {
        if ((findNote(id)) != null) {
            notes.remove(findNote(id));
            saveToXml();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAllNote(){
        try {
            notes.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeNote(INote note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
                return true;
            }
        }
        return false;
    }

    @Override
    public INote findNote(int id) {
        for (INote note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<INote> notes() {
        return notes;
    }

}
