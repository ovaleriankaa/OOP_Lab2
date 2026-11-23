package lab2.parser;

import lab2.model.Beer;
import lab2.model.Characteristics;
import lab2.model.Packaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxParser implements XmlParser {
    private static final Logger logger = LogManager.getLogger(StaxParser.class);

    @Override
    public List<Beer> parse(String xmlFilePath) {
        List<Beer> beerList = new ArrayList<>();
        logger.info("Початок StAX парсингу файлу: {}", xmlFilePath);

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(xmlFilePath));

            Beer currentBeer = null;
            Characteristics currentChars = null;
            Packaging currentPackaging = null;
            String currentElementName = "";

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    currentElementName = startElement.getName().getLocalPart();

                    if (currentElementName.equals("Beer")) {
                        currentBeer = new Beer();
                        currentBeer.setId(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue());
                    } else if (currentElementName.equals("Chars")) {
                        currentChars = new Characteristics();
                    } else if (currentElementName.equals("Packaging")) {
                        currentPackaging = new Packaging();
                        currentPackaging.setVolume(Double.parseDouble(startElement.getAttributeByName(new javax.xml.namespace.QName("volume")).getValue()));
                        currentPackaging.setMaterial(startElement.getAttributeByName(new javax.xml.namespace.QName("material")).getValue());
                    }
                } else if (event.isCharacters()) {
                    Characters characters = event.asCharacters();
                    String data = characters.getData().trim();
                    if (!data.isEmpty() && currentBeer != null) {
                        switch (currentElementName) {
                            case "Name" -> currentBeer.setName(data);
                            case "Type" -> currentBeer.setType(data);
                            case "Al" -> currentBeer.setAl(Boolean.parseBoolean(data));
                            case "Manufacturer" -> currentBeer.setManufacturer(data);
                            case "Ingredient" -> currentBeer.getIngredients().add(data);
                            case "AlcoholVolume" -> currentChars.setAlcoholVolume(Double.parseDouble(data));
                            case "Transparency" -> currentChars.setTransparency(Integer.parseInt(data));
                            case "Filtered" -> currentChars.setFiltered(Boolean.parseBoolean(data));
                            case "Calories" -> currentChars.setCalories(Integer.parseInt(data));
                        }
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String name = endElement.getName().getLocalPart();
                    currentElementName = ""; // Скидаємо поточний елемент
                    if (name.equals("Packaging")) {
                        currentChars.setPackaging(currentPackaging);
                    } else if (name.equals("Chars")) {
                        currentBeer.setChars(currentChars);
                    } else if (name.equals("Beer")) {
                        beerList.add(currentBeer);
                    }
                }
            }
            logger.info("StAX парсинг завершено. Знайдено елементів: {}", beerList.size());
        } catch (Exception e) {
            logger.error("Помилка StAX парсингу: ", e);
        }
        return beerList;
    }
}