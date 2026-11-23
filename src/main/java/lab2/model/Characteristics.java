package lab2.model;

public class Characteristics {
    private double alcoholVolume;
    private int transparency;
    private boolean filtered;
    private int calories;
    private Packaging packaging;

    public Characteristics() {}

    public double getAlcoholVolume() { return alcoholVolume; }
    public void setAlcoholVolume(double alcoholVolume) { this.alcoholVolume = alcoholVolume; }

    public int getTransparency() { return transparency; }
    public void setTransparency(int transparency) { this.transparency = transparency; }

    public boolean isFiltered() { return filtered; }
    public void setFiltered(boolean filtered) { this.filtered = filtered; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public Packaging getPackaging() { return packaging; }
    public void setPackaging(Packaging packaging) { this.packaging = packaging; }

    @Override
    public String toString() {
        return "Characteristics{" +
                "alcoholVolume=" + alcoholVolume +
                ", transparency=" + transparency +
                ", filtered=" + filtered +
                ", calories=" + calories +
                ", packaging=" + packaging +
                '}';
    }
}