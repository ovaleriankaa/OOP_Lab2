package lab2.comparator;

import lab2.model.Beer;
import java.util.Comparator;

public class BeerTransparencyComparator implements Comparator<Beer> {
    @Override
    public int compare(Beer b1, Beer b2) {
        return Integer.compare(b2.getChars().getTransparency(), b1.getChars().getTransparency());
    }
}