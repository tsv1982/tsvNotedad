package com.tsv.tsvnotedad.model;

import java.util.Date;

public interface INote {

    int getId();

    void setId(int id);

    String getTheme();

    void setTheme(String theme);

    String getTextNote();

    void setTextNote(String textNote);

    Date getDate(); // the date will be updated automatically when either text or title gets changed

    void setDate(Date date);

}
