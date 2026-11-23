package lab2.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidator {
    private static final Logger logger = LogManager.getLogger(XmlValidator.class);

    public static boolean validate(String xmlFilePath, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new File(xmlFilePath)));
            logger.info("Валідація пройшла успішно. Файл {} відповідає схемі {}", xmlFilePath, xsdFilePath);
            return true;
        } catch (SAXException e) {
            logger.error("Помилка валідації XML (невідповідність схемі): {}", e.getMessage());
            return false;
        } catch (IOException e) {
            logger.error("Помилка читання файлу: {}", e.getMessage());
            return false;
        }
    }
}