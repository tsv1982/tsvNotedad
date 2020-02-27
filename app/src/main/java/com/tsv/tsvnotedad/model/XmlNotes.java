package com.tsv.tsvnotedad.model;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlNotes implements IXmlNotes {

    private String pathToFile;

    public XmlNotes(String pathToFile) {
        this.pathToFile = pathToFile + "/notes.xml";
    }

    private boolean xmlWrite(Document document) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(pathToFile)));
            return true;
        } catch (Exception e) {
            Log.i("MyLog", String.valueOf(e));
            return false;
        }
    }

    private Document createNoteXml(List<INote> items) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element notes = document.createElement("notes");
            document.appendChild(notes);

            for (INote note : items) {
                Element element = document.createElement("note");
                element.setAttribute("id", String.valueOf(note.getId()));
                element.setAttribute("title", note.getTitle());
                element.setAttribute("text", note.getText());
                element.setAttribute("date", formatDateToXml(note.getDate()));
                notes.appendChild(element);
            }
            return document;

        } catch (Exception e) {
            return null;
        }
    }

    private Date formatDateFromXml(String str) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    private String formatDateToXml(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
        String str = dateFormatter.format(date);
        return str;
    }

    @Override
    public List<INote> loadFromXml() {

        List<INote> arr = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathToFile));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i) instanceof Element) {
                    if (((Element) nodeList.item(i)).hasAttribute("id")) {
                        Note note = new Note();
                        note.setDate(formatDateFromXml(((Element) nodeList.item(i)).getAttribute("date")));
                        note.setId(Integer.parseInt(((Element) nodeList.item(i)).getAttribute("id")));
                        note.setText(((Element) nodeList.item(i)).getAttribute("text"));
                        note.setTitle(((Element) nodeList.item(i)).getAttribute("title"));
                        arr.add(note);
                    }
                }
            }
        } catch (Exception e) {

        }
        return arr;
    }

    @Override
    public boolean saveToXml(List<INote> arr) {
        return xmlWrite(createNoteXml(arr));
    }


}




