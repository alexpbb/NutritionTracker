public class User {
    private String name;
    private int age;
    private double height;
    private double weight;
    private boolean loseWeight;
    private double caloriesToKeep;

    public User(String name, int age, double height, double weight, boolean loseWeight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.loseWeight = loseWeight;
        this.caloriesToKeep = (13.4 * weight) + (4.8 * 2.54 * height) - (5.68 * age) + 88.36;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getHeight() {
        return height;
    }
    public double getWeight() {
        return weight;
    }
    public double getCaloriesToKeep() {
        return caloriesToKeep;
    }
    public void setCaloriesToKeep() {
        this.caloriesToKeep = (13.4 * weight) + (4.8 * 2.54 * height) - (5.68 * age) + 88.36;
    }
    public boolean isLoseWeight() {
        return loseWeight;
    }
}