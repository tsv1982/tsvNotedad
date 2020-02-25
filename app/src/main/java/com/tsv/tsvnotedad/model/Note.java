package com.tsv.tsvnotedad.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root
public class Note implements INote {

    @Attribute(name = "id")
    private int id;
    @Element(name = "theme")
    private String theme;
    @Element(name = "textNote")
    private String textNote;
    @Element(name = "date")
    private Date date;

    public Note(@Attribute(name = "id") int id,
                @Element(name = "theme") String theme,
                @Element(name = "textNote") String textNote,
                @Element(name = "date") Date date) {
        this.id = id;
        this.theme = theme;
        this.textNote = textNote;
        this.date = date;
    }

    public Note() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTheme() {
        return theme;
    }

    @Override
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String getTextNote() {
        return textNote;
    }

    @Override
    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
