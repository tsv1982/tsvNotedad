package com.tsv.tsvnotedad.model;

import java.util.List;

public interface INotesModel {

    boolean addNote(IXmlNote note);

    boolean removeNote(int id);

    boolean removeAllNote();

    IXmlNote findNote(int id);

    List<IXmlNote> notes();

}

