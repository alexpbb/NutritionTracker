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

import javafx.scene.paint.Color;
import javafx.animation.TranslateTransition;
//import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
//import javafx.scene.layout.StackPane;
import javafx.animation.Interpolator;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

public class Home extends Application {
    private User user;
    private ObservableList<Food> data = FXCollections.observableArrayList();
    private double totalCals;
    //private ObservableList<String> filter = FXCollections.observableArrayList();

    private BorderPane pane;
    private VBox leftSide;
    private Text hello;
    private Label overview;
    private Label log;
    private TextField food;
    private TextField serv;
    private TextField cal;
    private Button addFood;
    private Button addFilter;
    private ComboBox<String> combo;
    private ProgressBar bar;
    private HBox hb;
    private VBox vb;
    private TableView<Food> table;
    private TableColumn<Food, String> col;
    private TableColumn<Food, String> col2;
    private TableColumn<Food, String> col3;

    private boolean isMenuOpen = false;
    private HBox menuBar;
    private Button homeButton;
    private Button toggleButton;
    private Button helpButton;
    private Button userButton;
    private Button languageButton;
    /*
    private VBox menuBar;
    private Button homeButton;
    private StackPane contentPane;
    */

    public Home(User user) {
        this.user = user;
    }

    public BorderPane newHome() {
        //Formatting
        pane = new BorderPane();
        hb = new HBox();
        hb.setSpacing(3);
        vb = new VBox();
        vb.setSpacing(5);
        vb.setPadding(new Insets(20, 40, 20, 40));
        hello = new Text("Hello, " + user.getName());
        hello.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        pane.setTop(hello);

        BorderPane menuBase = new BorderPane();
        menuBar = new HBox();
        menuBar.setSpacing(10);
        menuBar.setAlignment(Pos.CENTER);
        menuBar.setMinHeight(60);
        homeButton = new Button("\u2261");
        homeButton.setPrefSize(180, 120);
        homeButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        homeButton.setShape(new Circle(120));
        homeButton.setTranslateY(-60);
        toggleButton = new Button("Theme");
        languageButton = new Button("Language");
        userButton = new Button("User");
        helpButton = new Button("Help");
        menuBar.getChildren().addAll(languageButton, toggleButton, homeButton, helpButton, userButton);
        menuBar.setTranslateY(70);
        menuBase.setTop(menuBar);
        /*
        menuBar = createMenuBar();
        homeButton = createHomeButton();
        contentPane = new StackPane();
        contentPane.getChildren().addAll(homeButton);
        StackPane menuBase = new StackPane(contentPane, menuBar);
        */

        //Table
        table = new TableView<Food>();
        table.setEditable(true);
        col = new TableColumn<>("Food");
        col.setMinWidth(500);
        col2 = new TableColumn<>("Servings");
        col2.setMaxWidth(100);
        col3 = new TableColumn<>("Calories");
        col3.setMaxWidth(100);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        col.prefWidthProperty().bind(table.widthProperty().multiply(.6));
        col2.prefWidthProperty().bind(table.widthProperty().multiply(.2));
        col3.prefWidthProperty().bind(table.widthProperty().multiply(.2));

        table.setItems(data);
        table.getColumns().addAll(col, col2, col3);
        
        //Area under table
        food = new TextField();
        food.setPromptText("Enter food");
        serv = new TextField();
        serv.setPromptText("Enter servings");
        cal = new TextField();
        cal.setPromptText("Enter calories");
        addFood = new Button("+ADD FOOD");
        addFilter = new Button("FILTER");
        combo = new ComboBox<>();
        combo.setPadding(new Insets(0, 0, 0, 300));
        combo.getItems().addAll("Name", "Calories (Least to Greatest)", "Calories (Greatest to Least)");

        bar = new ProgressBar(0);
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

        HBox.setHgrow(combo, Priority.ALWAYS);

        vb.getChildren().addAll(table, hb, bar);

        VBox.setVgrow(table, Priority.ALWAYS);

        pane.setCenter(vb);
        pane.setBottom(menuBase);

        //Left margin
        leftSide = new VBox();
        leftSide.setMinWidth(150);
        overview = new Label("Overview");
        log  = new Label("Diary");
        overview.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        overview.setPadding(new Insets(0, 0, 10, 0));
        log.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        leftSide.getChildren().addAll(overview, log);
        pane.setLeft(leftSide);
        BorderPane.setMargin(leftSide, new Insets(20, 0, 20, 20));
        BorderPane.setMargin(hello, new Insets(10, 10, 0, 10));
        pane.setMinWidth(1200);

        if (ThemeManager.getCurrentMode() == ThemeManager.ThemeMode.DARK) {
            setDarkMode();
        } else {
            setLightMode();
        }

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
        
        //Function for "Home" button
        homeButton.setOnAction(e -> toggleMenuBar());

        toggleButton.setOnAction(e -> {
            ThemeManager.toggleMode();
            if (ThemeManager.getCurrentMode() == ThemeManager.ThemeMode.DARK) {
                setDarkMode();
            } else {
                setLightMode();
            }
        });

        return pane;
    }

    private void setDarkMode() {
        // Apply dark theme
        pane.setStyle("-fx-background-color: #393B49;");
        leftSide.setStyle("-fx-background-color: #393B49;");
        hello.setFill(Color.web("#FAF6EF"));
        overview.setStyle("-fx-text-fill: #FAF6EF;");
        log.setStyle("-fx-text-fill: #FAF6EF;");
        food.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        serv.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        cal.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        addFood.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        addFilter.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        combo.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        //bar.setStyle("-fx-accent: green; -fx-progress-color: green;");
        hb.setStyle("-fx-background-color: #393B49;");
        vb.setStyle("-fx-background-color: #393B49;");
        //table.setStyle("-fx-background-color: #444; -fx-text-fill: white;");
        //col.setStyle("-fx-background-color: #444; -fx-column-header-background: #444;");
        //col2.setStyle("-fx-background-color: #444;");
        //col3.setStyle("-fx-background-color: #444;");
        homeButton.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        menuBar.setStyle("-fx-background-color: #41455E;");
        languageButton.setStyle("-fx-background-color: #393B49; -fx-text-fill: #FAF6EF;");
        toggleButton.setStyle("-fx-background-color: #393B49; -fx-text-fill: #FAF6EF;");
        userButton.setStyle("-fx-background-color: #393B49; -fx-text-fill: #FAF6EF;");
        helpButton.setStyle("-fx-background-color: #393B49; -fx-text-fill: #FAF6EF;");
    }

    private void setLightMode() {
        // Apply light theme
        pane.setStyle("-fx-background-color: #F6EEDE;");
        leftSide.setStyle("-fx-background-color: #F6EEDE;");
        hello.setFill(Color.web("#41455E"));
        overview.setStyle("-fx-text-fill: #41455E;");
        log.setStyle("-fx-text-fill: #41455E;");
        food.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        serv.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        cal.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        addFood.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        addFilter.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        combo.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        //bar.setStyle("-fx-accent: green; -fx-progress-color: green;");
        hb.setStyle("-fx-background-color: #F6EEDE;");
        vb.setStyle("-fx-background-color: #F6EEDE;");
        homeButton.setStyle("-fx-background-color: #F9F4EA; -fx-text-fill: #41455E;");
        menuBar.setStyle("-fx-background-color: #F9F4EA;");
        languageButton.setStyle("-fx-background-color: #F6EEDE; -fx-text-fill: #41455E;");
        toggleButton.setStyle("-fx-background-color: #F6EEDE; -fx-text-fill: #41455E;");
        userButton.setStyle("-fx-background-color: #F6EEDE; -fx-text-fill: #41455E;");
        helpButton.setStyle("-fx-background-color: #F6EEDE; -fx-text-fill: #41455E;");
    }

    /*
    private VBox createMenuBar() {
        Rectangle background = new Rectangle(1240, 100, Color.LIGHTSLATEGRAY);
        VBox menuItems = new VBox(10);
        menuItems.getChildren().addAll(
            new Button("Profile"),
            new Button("Settings"),
            new Button("Help")
        );
        VBox.setMargin(menuItems, new Insets(10));

        VBox menuBar = new VBox(background, menuItems);
        menuBar.setLayoutY(800);
        return menuBar;
    }
    */

    /*
    private Button createHomeButton() {
        homeButton = new Button();
        homeButton.setPrefSize(100, 100);
        homeButton.setText("ND");
        homeButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        homeButton.setShape(new Circle(50));
        return homeButton;
    }
    */

    private void toggleMenuBar() {
        /*
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), menuBar);
        if (isMenuOpen) {
            tt.setToY(0);
            isMenuOpen = false;
        } else {
            tt.setToY(-100);
            isMenuOpen = true;
        }
        tt.play();
        */
        isMenuOpen = !isMenuOpen;
        double endY = isMenuOpen ? 0 : 70;
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), menuBar);
        tt.setToY(endY);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.play();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}