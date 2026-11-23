package lab2.parser;

import lab2.model.Beer;
import lab2.model.Characteristics;
import lab2.model.Packaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxParser implements XmlParser {
    private static final Logger logger = LogManager.getLogger(SaxParser.class);

    @Override
    public List<Beer> parse(String xmlFilePath) {
        logger.info("Початок SAX парсингу файлу: {}", xmlFilePath);
        try {
            javax.xml.parsers.SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            BeerHandler handler = new BeerHandler();
            saxParser.parse(new File(xmlFilePath), handler);
            logger.info("SAX парсинг завершено. Знайдено елементів: {}", handler.getBeerList().size());
            return handler.getBeerList();
        } catch (Exception e) {
            logger.error("Помилка SAX парсингу: ", e);
            return new ArrayList<>();
        }
    }
}

class BeerHandler extends DefaultHandler {
    private List<Beer> beerList = new ArrayList<>();
    private Beer currentBeer;
    private Characteristics currentChars;
    private Packaging currentPackaging;
    private StringBuilder elementValue;

    public List<Beer> getBeerList() { return beerList; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        elementValue = new StringBuilder();
        if (qName.equals("Beer")) {
            currentBeer = new Beer();
            currentBeer.setId(attributes.getValue("id"));
        } else if (qName.equals("Chars")) {
            currentChars = new Characteristics();
        } else if (qName.equals("Packaging")) {
            currentPackaging = new Packaging();
            currentPackaging.setVolume(Double.parseDouble(attributes.getValue("volume")));
            currentPackaging.setMaterial(attributes.getValue("material"));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue != null) elementValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String value = elementValue.toString().trim();
        switch (qName) {
            case "Name" -> currentBeer.setName(value);
            case "Type" -> currentBeer.setType(value);
            case "Al" -> currentBeer.setAl(Boolean.parseBoolean(value));
            case "Manufacturer" -> currentBeer.setManufacturer(value);
            case "Ingredient" -> currentBeer.getIngredients().add(value);
            case "AlcoholVolume" -> currentChars.setAlcoholVolume(Double.parseDouble(value));
            case "Transparency" -> currentChars.setTransparency(Integer.parseInt(value));
            case "Filtered" -> currentChars.setFiltered(Boolean.parseBoolean(value));
            case "Calories" -> currentChars.setCalories(Integer.parseInt(value));
            case "Packaging" -> currentChars.setPackaging(currentPackaging);
            case "Chars" -> currentBeer.setChars(currentChars);
            case "Beer" -> beerList.add(currentBeer);
        }
    }
}