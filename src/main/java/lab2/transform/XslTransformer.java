package lab2.transform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XslTransformer {
    private static final Logger logger = LogManager.getLogger(XslTransformer.class);

    public static void transform(String xmlPath, String xslPath, String outputPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslStream = new StreamSource(new File(xslPath));
            Transformer transformer = factory.newTransformer(xslStream);

            StreamSource in = new StreamSource(new File(xmlPath));
            StreamResult out = new StreamResult(new File(outputPath));

            transformer.transform(in, out);
            logger.info("XSL трансформація успішно завершена. Новий файл створено: {}", outputPath);
        } catch (Exception e) {
            logger.error("Помилка під час XSL трансформації: ", e);
        }
    }
}