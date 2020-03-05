package com.tsv.tsvnotedad.model;

import java.util.Date;

public interface INote {

    int getId();

    String getTitle();
    void setTitle(String title);

    String getText();
    void setText(String text);

    Date getTime();

}
