package lab2.model;

import java.util.ArrayList;
import java.util.List;

public class Beer {
    private String id;
    private String name;
    private String type;
    private boolean al;
    private String manufacturer;
    private List<String> ingredients = new ArrayList<>();
    private Characteristics chars;

    public Beer() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isAl() { return al; }
    public void setAl(boolean al) { this.al = al; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public Characteristics getChars() { return chars; }
    public void setChars(Characteristics chars) { this.chars = chars; }

    @Override
    public String toString() {
        return "Beer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", alcoholic=" + al +
                ", manufacturer='" + manufacturer + '\'' +
                ", ingredients=" + ingredients +
                ", chars=" + chars +
                "}\n";
    }
}