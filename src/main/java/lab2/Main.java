package lab2;

import lab2.comparator.BeerTransparencyComparator;
import lab2.model.Beer;
import lab2.parser.DomParser;
import lab2.parser.SaxParser;
import lab2.parser.StaxParser;
import lab2.parser.XmlParser;
import lab2.validator.XmlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String xmlPath = "src/main/resources/beer.xml";
        String xsdPath = "src/main/resources/beer.xsd";

        logger.info("-Валідація XML");
        boolean isValid = XmlValidator.validate(xmlPath, xsdPath);
        if (!isValid) {
            logger.error("Роботу припинено: XML файл невалідний!");
            return;
        }

        logger.info("Парсинг DOM");
        XmlParser domParser = new DomParser();
        List<Beer> domBeers = domParser.parse(xmlPath);

        logger.info("Парсинг SAX");
        XmlParser saxParser = new SaxParser();
        List<Beer> saxBeers = saxParser.parse(xmlPath);

        logger.info("Парсинг StAX");
        XmlParser staxParser = new StaxParser();
        List<Beer> staxBeers = staxParser.parse(xmlPath);

        logger.info("Сортування результатів (StAX) за прозорістю");
        staxBeers.sort(new BeerTransparencyComparator());
        for (Beer beer : staxBeers) {
            logger.info("Пиво: {} | Прозорість: {}%", beer.getName(), beer.getChars().getTransparency());
        }

        logger.info("XSL Трансформація");
        String xslPath = "src/main/resources/transform.xsl";
        String transformedXmlPath = "src/main/resources/transformed_beer.xml";
        lab2.transform.XslTransformer.transform(xmlPath, xslPath, transformedXmlPath);
    }
}