package edu.uniba.di.lacam.kdde.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Emotion {

    private static final InputStream EMOTION = Emotion.class.getResourceAsStream(File.separator + "a-hierarchy.xml");

    private Map<String, String> emotions;

    private static final Emotion emotion = new Emotion();

    private Emotion() {
        try {
            loadEmotions();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private synchronized void loadEmotions() throws ParserConfigurationException, IOException, SAXException {
        emotions = new HashMap<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(EMOTION);
        doc.getDocumentElement().normalize();
        NodeList categories = doc.getElementsByTagName("categ");
        for (int i = 0; i < categories.getLength(); i++) {
            Element category = (Element) categories.item(i);
            String name = category.getAttribute("name");
            if (name.equals("root")) emotions.put("root", null);
            else emotions.put(name, category.getAttribute("isa"));
        }
    }

    public static Emotion getInstance() {
        return emotion;
    }
}