import java.util.ArrayList;

public class Meal {
    ArrayList<String> ingredients;
    double calories;

    public Meal(ArrayList<String> list, double calories) {
        ingredients = new ArrayList<>(list);
        this.calories = calories;
        //this.serving = serving;
    }

    public void removeIngr(String target) {
        for(int i = 0; i < ingredients.size(); i++) {
            if(target.equals(ingredients.get(i))) {
                ingredients.remove(i);
            }
        }
    }

    public void addIngr(String ing) {
        ingredients.add(ing);
    }
}