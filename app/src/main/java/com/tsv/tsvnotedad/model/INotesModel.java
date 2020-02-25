package com.tsv.tsvnotedad.model;

import java.util.List;

public interface INotesModel {

    boolean addNote(INote note);

    boolean removeNote(int id);

    boolean removeAllNote();

    boolean changeNote(INote note);

    INote findNote(int id);
    List<INote> notes();
}
