package lab2.parser;

import lab2.model.Beer;
import java.util.List;

public interface XmlParser {
    List<Beer> parse(String xmlFilePath);
}