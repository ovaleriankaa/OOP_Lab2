package lab2;

import lab2.comparator.BeerTransparencyComparator;
import lab2.model.Beer;
import lab2.model.Characteristics;
import lab2.parser.DomParser;
import lab2.parser.SaxParser;
import lab2.parser.StaxParser;
import lab2.parser.XmlParser;
import lab2.validator.XmlValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Lab2Tests {

    private final String XML_PATH = "src/main/resources/beer.xml";
    private final String XSD_PATH = "src/main/resources/beer.xsd";

    @Test
    void testXmlValidation() {
        assertTrue(XmlValidator.validate(XML_PATH, XSD_PATH), "Валідація правильного XML має повертати true");
    }

    @Test
    void testDomParser() {
        XmlParser parser = new DomParser();
        List<Beer> beers = parser.parse(XML_PATH);

        assertNotNull(beers);
        assertEquals(5, beers.size(), "DOM парсер має знайти 5 елементів");
        assertEquals("Оболонь Світле", beers.get(0).getName(), "Назва першого пива має бути Оболонь Світле");
    }

    @Test
    void testSaxParser() {
        XmlParser parser = new SaxParser();
        List<Beer> beers = parser.parse(XML_PATH);

        assertNotNull(beers);
        assertEquals(5, beers.size(), "SAX парсер має знайти 5 елементів");
        assertEquals("Львівське Різдвяне", beers.get(1).getName(), "Назва другого пива має бути Львівське Різдвяне");
    }

    @Test
    void testStaxParser() {
        XmlParser parser = new StaxParser();
        List<Beer> beers = parser.parse(XML_PATH);

        assertNotNull(beers);
        assertEquals(5, beers.size(), "StAX парсер має знайти 5 елементів");
        assertEquals("Оболонь Безалкогольне", beers.get(4).getName(), "Назва останнього пива має бути Оболонь Безалкогольне");
    }

    @Test
    void testComparator() {
        Beer b1 = new Beer();
        Characteristics c1 = new Characteristics();
        c1.setTransparency(15);
        b1.setChars(c1);

        Beer b2 = new Beer();
        Characteristics c2 = new Characteristics();
        c2.setTransparency(96);
        b2.setChars(c2);

        List<Beer> list = new ArrayList<>(List.of(b1, b2));

        list.sort(new BeerTransparencyComparator());

        assertEquals(96, list.get(0).getChars().getTransparency());
        assertEquals(15, list.get(1).getChars().getTransparency());
    }

    @Test
    void testXslTransformer() {
        String xslPath = "src/main/resources/transform.xsl";
        String testOutputPath = "src/main/resources/test_transformed_output.xml";

        lab2.transform.XslTransformer.transform(XML_PATH, xslPath, testOutputPath);

        java.io.File outFile = new java.io.File(testOutputPath);
        assertTrue(outFile.exists(), "Трансформований файл має бути створений");
        assertTrue(outFile.length() > 0, "Трансформований файл не має бути порожнім");

        outFile.delete();
    }

    @Test
    void testInvalidXmlValidation() {
        boolean isValid = XmlValidator.validate("src/main/resources/fake_file.xml", XSD_PATH);
        assertFalse(isValid, "Валідація неіснуючого або зламаного файлу має повертати false");
    }
}