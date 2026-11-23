package lab2.parser;

import lab2.model.Beer;
import lab2.model.Characteristics;
import lab2.model.Packaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomParser implements XmlParser {
    private static final Logger logger = LogManager.getLogger(DomParser.class);

    @Override
    public List<Beer> parse(String xmlFilePath) {
        List<Beer> beerList = new ArrayList<>();
        logger.info("Початок DOM парсингу файлу: {}", xmlFilePath);

        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList beerNodes = document.getElementsByTagName("Beer");
            for (int i = 0; i < beerNodes.getLength(); i++) {
                Node node = beerNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Beer beer = new Beer();
                    beer.setId(element.getAttribute("id"));
                    beer.setName(getElementTextContent(element, "Name"));
                    beer.setType(getElementTextContent(element, "Type"));
                    beer.setAl(Boolean.parseBoolean(getElementTextContent(element, "Al")));
                    beer.setManufacturer(getElementTextContent(element, "Manufacturer"));

                    NodeList ingredients = element.getElementsByTagName("Ingredient");
                    for (int j = 0; j < ingredients.getLength(); j++) {
                        beer.getIngredients().add(ingredients.item(j).getTextContent());
                    }

                    Element charsEl = (Element) element.getElementsByTagName("Chars").item(0);
                    Characteristics chars = new Characteristics();
                    chars.setAlcoholVolume(Double.parseDouble(getElementTextContent(charsEl, "AlcoholVolume")));
                    chars.setTransparency(Integer.parseInt(getElementTextContent(charsEl, "Transparency")));
                    chars.setFiltered(Boolean.parseBoolean(getElementTextContent(charsEl, "Filtered")));
                    chars.setCalories(Integer.parseInt(getElementTextContent(charsEl, "Calories")));

                    Element packEl = (Element) charsEl.getElementsByTagName("Packaging").item(0);
                    Packaging packaging = new Packaging();
                    packaging.setVolume(Double.parseDouble(packEl.getAttribute("volume")));
                    packaging.setMaterial(packEl.getAttribute("material"));

                    chars.setPackaging(packaging);
                    beer.setChars(chars);
                    beerList.add(beer);
                }
            }
            logger.info("DOM парсинг завершено. Знайдено елементів: {}", beerList.size());
        } catch (Exception e) {
            logger.error("Помилка DOM парсингу: ", e);
        }
        return beerList;
    }

    private String getElementTextContent(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }
}