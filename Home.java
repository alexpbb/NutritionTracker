import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Home extends Application{
    private User user;
    private ObservableList<Food> data = FXCollections.observableArrayList();
    private double totalCals;
    //private ObservableList<String> filter = FXCollections.observableArrayList();

    public Home(User user) {
        this.user = user;
    }

    public BorderPane newHome() {
        //Formatting
        BorderPane pane = new BorderPane();
        HBox hb = new HBox();
        hb.setSpacing(3);
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setPadding(new Insets(20, 40, 20, 40));
        Text hello = new Text("Hello, " + user.getName());
        hello.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        pane.setTop(hello);

        //Table
        TableView<Food> table = new TableView<Food>();
        table.setEditable(true);
        TableColumn<Food, String> col = new TableColumn("Food");
        col.setMinWidth(500);
        TableColumn<Food, String> col2 = new TableColumn("Servings");
        col2.setMaxWidth(100);
        TableColumn<Food, String> col3 = new TableColumn("Calories");
        col3.setMaxWidth(100);
        table.setItems(data);
        table.getColumns().addAll(col, col2, col3);
        
        //Area under table
        TextField food = new TextField();
        food.setPromptText("Enter food");
        TextField serv = new TextField();
        serv.setPromptText("Enter servings");
        TextField cal = new TextField();
        cal.setPromptText("Enter calories");
        Button addFood = new Button("+ADD FOOD");
        Button addFilter = new Button("FILTER");
        ComboBox combo = new ComboBox();
        combo.setPadding(new Insets(0, 0, 0, 300));
        combo.getItems().addAll(
            "Name",
            "Calories (Least to Greatest)",
            "Calories (Greatest to Least)"
        );

        ProgressBar bar = new ProgressBar(0);
        //bar.setMinHeight(25);
        bar.setPadding(new Insets(50, 0, 0, 0));
        bar.setMinHeight(75);
        bar.setMinWidth(250);
        if (user.isLoseWeight()) {
            bar.setStyle("-fx-accent: green;");
        } else {
            bar.setStyle("-fx-accent: red;");
        }

        //Adds all nodes
        hb.getChildren().addAll(food, serv, cal, addFood, combo, addFilter);
        vb.getChildren().addAll(table, hb, bar);
        pane.setCenter(vb);

        //Left margin
        VBox leftSide = new VBox();
        leftSide.setMinWidth(150);
        Label overview = new Label("Overview");
        Label log  = new Label("Diary");
        overview.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        overview.setPadding(new Insets(0, 0, 10, 0));
        log.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        leftSide.getChildren().addAll(overview, log);
        pane.setLeft(leftSide);
        pane.setMargin(leftSide, new Insets(20, 0, 20, 20));
        pane.setMargin(hello, new Insets(10, 10, 0, 10));

        //Function for "+ADD FOOD" button
        addFood.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) throws IllegalArgumentException {
                if (!food.getText().isEmpty() && !serv.getText().isEmpty() && !cal.getText().isEmpty()) {
                    Food meal = new Food(food.getText(), Double.parseDouble(cal.getText()), Double.parseDouble(serv.getText()));
                    col.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
                    col2.setCellValueFactory(new PropertyValueFactory<>("serving"));
                    col3.setCellValueFactory(new PropertyValueFactory<>("calories"));
                    data.add(meal);
                    food.clear();
                    serv.clear();
                    cal.clear();
                    totalCals += meal.getCalories() / user.getCaloriesToKeep();
                    bar.setProgress(totalCals);
                    if (totalCals * user.getCaloriesToKeep() > .75 * user.getCaloriesToKeep() && user.isLoseWeight()) {
                        bar.setStyle("-fx-accent: red;");
                    } else if (totalCals * user.getCaloriesToKeep() > .75 * user.getCaloriesToKeep() && !user.isLoseWeight()) {
                        bar.setStyle("-fx-accent: green;");
                    } else if (totalCals * user.getCaloriesToKeep() > .5 * user.getCaloriesToKeep()) {
                        bar.setStyle("-fx-accent: yellow;");
                    }
                } else {
                    Label message = new Label("Enter info.");
                    vb.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 7);
                    vb.getChildren().add(message);
                }
            }
        });

        //Fuction of "FILTER" button
        addFilter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (combo.getValue().equals("Name")) {
                    for (int i = 1; i < data.size(); i++) {
                        Food temp = data.get(i);
                        int j = i - 1;
                        while (j >= 0 && data.get(j).compareTo(temp) > 0) {
                            data.set(j + 1, data.get(j));
                            j--;
                        }
                        data.set(j + 1, temp);
                    }
                }
                if (combo.getValue().equals("Calories (Least to Greatest)")) {
                    for (int i = 1; i < data.size(); i++) {
                        Food temp = data.get(i);
                        int j = i - 1;
                        while (j >= 0 && (temp.getCalories() - data.get(j).getCalories()) < 0) {
                            data.set(j + 1, data.get(j));
                            j--;
                        }
                        data.set(j + 1, temp);
                    }
                }
                if (combo.getValue().equals("Calories (Greatest to Least)")) {
                    for (int i = 1; i < data.size(); i++) {
                        Food temp = data.get(i);
                        int j = i - 1;
                        while (j >= 0 && (temp.getCalories() - data.get(j).getCalories()) > 0) {
                            data.set(j + 1, data.get(j));
                            j--;
                        }
                        data.set(j + 1, temp);
                    }
                }
                table.setItems(data);
            }
        });
        return pane;
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}