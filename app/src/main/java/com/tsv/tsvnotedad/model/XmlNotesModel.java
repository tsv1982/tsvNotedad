package com.tsv.tsvnotedad.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlNotesModel implements INotesModel {

    private static XmlNotesModel instance;
    private List<INote> notes;
    private String pathToFile;
    private int notesLastId;

    private XmlNotesModel(String pathToFile) {
        notes = new ArrayList<>();
        this.pathToFile = pathToFile + "/notes.xml";
        loadFromXml();
    }

    public static XmlNotesModel getInstance(String url) {
        if (instance == null) {
            instance = new XmlNotesModel(url);
        }
        return instance;
    }

    private boolean loadFromXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathToFile));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    XmlNote xmlNote = new XmlNote();
                    if (xmlNote.loadFromXml((Element) nodeList.item(i))) {
                        notes.add(xmlNote);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean saveToXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element nodes = document.createElement("notes");
            document.appendChild(nodes);

            for (INote note : notes) {
                Element element = document.createElement("note");
                XmlNote xmlNote = (XmlNote) note;
                if (xmlNote.saveToXml(element)) {
                    nodes.appendChild(element);
                }
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(pathToFile)));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean changeNote(INote note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == note.getId()) {
                notes.set(i, note);
                saveToXml();
                return true;
            }
        }
        return false;
    }

    private INote makeEmptyNote() {
        if (findNote(++notesLastId) != null) {
            makeEmptyNote();
        }
        return new XmlNote(notesLastId);
    }

    @Override
    public boolean addNote(INote note) {
        if (findNote(note.getId()) != null) {
            return changeNote(note);
        } else {
            XmlNote makeNote = (XmlNote) makeEmptyNote();
            makeNote.setTitle(note.getTitle());
            makeNote.setText(note.getText());
            makeNote.setTime(note.getTime());
            notes.add(makeNote);
            saveToXml();
            return true;
        }
    }

    @Override
    public boolean removeNote(int id) {
        INote note = findNote(id);
        if (note != null) {
            notes.remove(note);
            saveToXml();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAllNote() {
        try {
            notes.clear();
            saveToXml();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public INote findNote(int id) {
        for (INote note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<INote> notes() {
        return notes;
    }

}
