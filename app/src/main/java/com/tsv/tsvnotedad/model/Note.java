package com.tsv.tsvnotedad.model;

import java.security.SecureRandom;
import java.util.Date;

public class Note implements INote {

    private int id = 0;
    private String title;
    private String text;
    private Date date;

    public Note(int id, String title, String text) {
        if (id == 0) {
            this.id = new SecureRandom().nextInt(99999);
        } else {
            this.id = id;
        }
        this.title = title;
        this.text = text;
        this.date = new Date();
    }

    public Note() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
