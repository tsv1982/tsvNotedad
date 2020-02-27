package com.tsv.tsvnotedad.model;

import java.util.ArrayList;
import java.util.List;

public class XmlNotesModel implements INotesModel {

    private static XmlNotesModel instance;
    private IXmlNotes xmlNotes;
    private List<INote> notes;

    private XmlNotesModel(String pathToFile) {
        xmlNotes = new XmlNotes(pathToFile);
        loadFromXml();
    }

    public static XmlNotesModel getInstance(String url) {
        if (instance == null) {
            instance = new XmlNotesModel(url);
        }
        return instance;
    }

    private boolean loadFromXml() {
        notes = xmlNotes.loadFromXml();
        if (notes == null) {
            notes = new ArrayList<>();
            return false;
        } else {
            return true;
        }
    }

    private boolean saveToXml(List<INote> notes) {
        return xmlNotes.saveToXml(notes);
    }

    private boolean changeNote(INote note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
                saveToXml(notes);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addNote(INote note) {
        if (findNote(note.getId()) != null) {
            return changeNote(note);
        } else {
            notes.add(note);
            saveToXml(notes);
            return true;
        }
    }

    @Override
    public boolean removeNote(int id) {
        INote note = findNote(id);
        if (note != null) {
            notes.remove(note);
            saveToXml(notes);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAllNote() {
        try {
            notes.clear();
            saveToXml(notes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
