import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import javafx.scene.paint.Color;

public class SignUp extends Application {

    private TextField fNameField;
    private TextField lNameField;
    private Button submit;
    private GridPane grid;
    private Text welcome;
    private Button toggleButton;
    private Label fN;
    private Label lN;

    private GridPane grid2;
    private Text welcome2;
    private TextField ageField;
    private TextField heightField;
    private TextField weightField;
    private Button submit2;
    private Label a;
    private Label h;
    private Label w;
    private Text goal;
    private RadioButton lose;
    private RadioButton gain;
    private Button toggleButton2;


    @Override
    public void start(Stage primaryStage) {
        
        //creates the grid for the sign up page.
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        //grid.setGridLinesVisible(true);

        grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setVgap(5);
        grid2.setHgap(10);
        grid2.setPadding(new Insets(25));
        //grid2.setGridLinesVisible(true);

        //Scene ONE
        welcome = new Text("NUTRITION TRACKER");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(welcome, 2, 0);
        fNameField = new TextField();
        lNameField = new TextField();
        submit = new Button("Submit");

        toggleButton = new Button("Toggle Theme");

        fNameField.setPromptText("Enter first name");
        lNameField.setPromptText("Enter last name");
        grid.add(fNameField, 2, 1);
        grid.add(lNameField, 2, 2);
        grid.add(submit, 3, 3);

        grid.add(toggleButton, 2, 3);

        fN = new Label("First Name");
        lN = new Label("Last Name");
        grid.add(fN, 1, 1);
        grid.add(lN, 1, 2);

        Scene scene = new Scene(grid, 500, 450);
        primaryStage.setTitle("Nutrition Diary");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Scene TWO
        Scene scene1 = new Scene(grid2, 500, 450);
        welcome2 = new Text("USER INFO");
        welcome2.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid2.add(welcome2, 2, 0);
        ageField = new TextField();
        heightField = new TextField();
        weightField = new TextField();

        toggleButton2 = new Button("Toggle Theme");

        submit2 = new Button("Submit");
        ageField.setPromptText("Enter age");
        heightField.setPromptText("Enter height (in)");
        weightField.setPromptText("Enter weight (kg)");
        grid2.add(ageField, 2, 1);
        grid2.add(heightField, 2, 2);
        grid2.add(weightField, 2, 3);
        grid2.add(submit2, 3, 7);

        grid2.add(toggleButton2, 2, 7);

        a = new Label("Age");
        h = new Label("Height (in)");
        w = new Label("Weight (kg)");
        grid2.add(a, 1, 1);
        grid2.add(h, 1, 2);
        grid2.add(w, 1, 3);

        goal = new Text("Purpose for Diet:");
        lose = new RadioButton("Lose Weight");
        gain = new RadioButton("Gain Weight");
        grid2.add(goal, 2,4);
        grid2.add(lose, 2,5);
        grid2.add(gain, 2,6);

        // Apply light theme
        setLightTheme();

        //Event Handlers

        toggleButton.setOnAction(e -> {
            ThemeManager.toggleMode();
            if (ThemeManager.getCurrentMode() == ThemeManager.ThemeMode.DARK) {
                setDarkTheme();
            } else {
                setLightTheme();
            }
        });

        toggleButton2.setOnAction(e -> {
            ThemeManager.toggleMode();
            if (ThemeManager.getCurrentMode() == ThemeManager.ThemeMode.DARK) {
                setDarkTheme();
            } else {
                setLightTheme();
            }
        });

        submit.setOnAction (new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) throws IllegalArgumentException {
                if (!fNameField.getText().isEmpty() && !lNameField.getText().isEmpty()) {
                    primaryStage.setScene(scene1);
                    primaryStage.show();

                } else {
                    Label message = new Label("Invalid input.");
                    grid.add(message, 1, 4);
                }
            }
        });

        submit2.setOnAction (new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) throws IllegalArgumentException {
                if (!ageField.getText().isEmpty() && !heightField.getText().isEmpty()  && !weightField.getText().isEmpty() 
                    && Double.parseDouble(ageField.getText()) >= 13.0 && Double.parseDouble(heightField.getText()) > 0 && Double.parseDouble(weightField.getText()) > 0) {
                    String name = fNameField.getText() + " " + lNameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    if ((!lose.isSelected() && !gain.isSelected()) || (lose.isSelected() && gain.isSelected())) {
                        Label message3 = new Label("Select one goal.");
                        grid2.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 7);
                        grid2.add(message3, 1, 7);
                    } else if (lose.isSelected()) {
                        User user = new User(name, age, height, weight, true);
                        Home menu = new Home(user);
                        Scene scene2 = new Scene(menu.newHome(), 1240, 640);
                        primaryStage.setScene(scene2);
                        primaryStage.show();

                    } else {
                        User user = new User(name, age, height, weight, false);
                        Home menu = new Home(user);
                        Scene scene2 = new Scene(menu.newHome(), 1240, 800);
                        primaryStage.setScene(scene2);
                        primaryStage.show();
                    }
                } else {
                    Label message2 = new Label("Invalid input.");
                    grid2.add(message2, 1, 7);
                }
            }
        });
    }

    private void setDarkTheme() {
        // Apply dark theme
        fNameField.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        lNameField.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        submit.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        toggleButton.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        fN.setTextFill(Color.web("#FAF6EF"));
        lN.setTextFill(Color.web("#FAF6EF"));
        grid.setStyle("-fx-background-color: #393B49;");
        welcome.setFill(Color.web("#FAF6EF"));

        welcome2.setFill(Color.web("#FAF6EF"));
        ageField.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        heightField.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        weightField.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        submit2.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        a.setTextFill(Color.web("#FAF6EF"));
        h.setTextFill(Color.web("#FAF6EF"));
        w.setTextFill(Color.web("#FAF6EF"));
        goal.setFill(Color.web("#FAF6EF"));
        lose.setTextFill(Color.web("#FAF6EF"));
        gain.setTextFill(Color.web("#FAF6EF"));
        grid2.setStyle("-fx-background-color: #393B49;");
        toggleButton2.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
    }

    private void setLightTheme() {
        // Apply light theme
        fNameField.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        lNameField.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        submit.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        toggleButton.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
        fN.setTextFill(Color.web("#41455E"));
        lN.setTextFill(Color.web("#41455E"));
        grid.setStyle("-fx-background-color: #F6EEDE;");
        welcome.setFill(Color.web("#41455E"));

        welcome2.setFill(Color.web("#41455E"));
        ageField.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        heightField.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        weightField.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        submit2.setStyle("-fx-background-color: #FAF6EF; -fx-text-fill: #41455E;");
        a.setTextFill(Color.web("#41455E"));
        h.setTextFill(Color.web("#41455E"));
        w.setTextFill(Color.web("#41455E"));
        goal.setFill(Color.web("#41455E"));
        lose.setTextFill(Color.web("#41455E"));
        gain.setTextFill(Color.web("#41455E"));
        grid2.setStyle("-fx-background-color: #F6EEDE;");
        toggleButton2.setStyle("-fx-background-color: #41455E; -fx-text-fill: #FAF6EF;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}