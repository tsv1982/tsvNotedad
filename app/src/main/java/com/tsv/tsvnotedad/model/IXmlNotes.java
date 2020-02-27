package com.tsv.tsvnotedad.model;

import java.util.List;

public interface IXmlNotes {

    List<INote> loadFromXml();

    boolean saveToXml(List<INote> arr);

}
