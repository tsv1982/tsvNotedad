package com.tsv.tsvnotedad.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class XmlNotes {

    private List<INote> notes;

    public XmlNotes(@ElementList(name = "notes") List<INote> notes) {
        this.notes = notes;
    }

    @ElementList(name = "notes")
    public List<INote> getNotes() {
        return notes;
    }

}




