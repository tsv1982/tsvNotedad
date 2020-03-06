package com.tsv.tsvnotedad.model;

import org.w3c.dom.Element;

import java.util.Date;

public class XmlNote implements INote {

    private int id;
    private String title;
    private String text;
    private Date time;

    public XmlNote() {
    }

    public XmlNote(int id) {
        this.id = id;
    }

    public XmlNote(int id, String title, String text) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.time = new Date();
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
                this.time = new Date(Long.parseLong(node.getAttribute("time")));
            return true;
        } else {
            return false;
        }
    }

    public boolean saveToXml(Element element){
        if (element != null) {
            element.setAttribute("id", String.valueOf(id));
            element.setAttribute("title", title);
            element.setAttribute("text", text);
            element.setAttribute("time", String.valueOf(time.getTime()));
            return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return id;
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
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
