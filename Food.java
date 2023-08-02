public class Food implements Comparable {
    private String ingredients;
    private double calories;
    private double serving;

    public Food(String ingredients, double calories, double serving) {
        this.ingredients = ingredients;
        this.calories = calories;
        this.serving = serving;
    }

    public String getIngredients() {
        return ingredients;
    }
    public double getCalories() {
        return calories;
    }
    public double getServing() {
        return serving;
    }
    
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public void setCalories(double cal) {
        this.calories = cal;
    }
    public void setServing(double serv) {
        this.serving = serv;
    }

    public int compareTo(Object object) {
        Food obj = (Food) object;
        if (this.ingredients.equals(obj.ingredients)) {
            return (int) (this.calories - obj.calories);
        }
        return this.ingredients.compareTo(obj.ingredients);
    }
}