public class User {
    private String name;
    private int age;
    private double height;
    private double weight;
    private boolean loseWeight;

    public User(String name, int age, double height, double weight, boolean loseWeight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.loseWeight = loseWeight;
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
}