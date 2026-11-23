package lab2.model;

public class Packaging {
    private double volume;
    private String material;

    public Packaging() {}

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    @Override
    public String toString() {
        return "Packaging{volume=" + volume + ", material='" + material + "'}";
    }
}