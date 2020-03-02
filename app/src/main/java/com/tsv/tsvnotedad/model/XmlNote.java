package com.tsv.tsvnotedad.model;

import org.w3c.dom.Element;

import java.util.Date;

public class XmlNote implements IXmlNote {

    private int id;
    private String title;
    private String text;
    private long time;

    public XmlNote(int id, String title, String text) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.time = new Date().getTime();
    }

    public XmlNote() {
    }

    public boolean loadFromXml(Element node) {
        if (node != null) {

            try {
                int id = Integer.parseInt(node.getAttribute("id"));
                this.id = id;
            } catch (NumberFormatException e) {
                return false;
            }

            String title = node.getAttribute("title");
            String text = node.getAttribute("text");

            if (text == null || text == "" || title == null || title == "") {
                return false;
            } else {
                this.title = title;
                this.text = text;
            }

            try {
                long date = Long.parseLong(node.getAttribute("time"));
                this.time = date;
            } catch (NumberFormatException e) {
                this.time = 0L;
            }

            return true;
        } else {
            return false;
        }
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
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
