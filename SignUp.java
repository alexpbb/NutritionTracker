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

public class SignUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        //creates the grid for the sign up page.
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        //grid.setGridLinesVisible(true);

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setVgap(5);
        grid2.setHgap(10);
        grid2.setPadding(new Insets(25));
        //grid2.setGridLinesVisible(true);

        //Scene ONE
        Text welcome = new Text("NUTRITION TRACKER");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(welcome, 2, 0);
        TextField fNameField = new TextField();
        TextField lNameField = new TextField();
        Button submit = new Button("Submit");
        fNameField.setPromptText("Enter first name");
        lNameField.setPromptText("Enter last name");
        grid.add(fNameField, 2, 1);
        grid.add(lNameField, 2, 2);
        grid.add(submit, 3, 3);

        Label fN = new Label("First Name");
        Label lN = new Label("Last Name");
        grid.add(fN, 1, 1);
        grid.add(lN, 1, 2);

        Scene scene = new Scene(grid, 500, 450);
        primaryStage.setTitle("Nutrition Diary");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Scene TWO
        Scene scene1 = new Scene(grid2, 500, 450);
        Text welcome2 = new Text("USER INFO");
        welcome2.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid2.add(welcome2, 2, 0);
        TextField ageField = new TextField();
        TextField heightField = new TextField();
        TextField weightField = new TextField();

        Button submit2 = new Button("Submit");
        ageField.setPromptText("Enter age");
        heightField.setPromptText("Enter height (inches)");
        weightField.setPromptText("Enter weight (kg)");
        grid2.add(ageField, 2, 1);
        grid2.add(heightField, 2, 2);
        grid2.add(weightField, 2, 3);
        grid2.add(submit2, 3, 7);

        Label a = new Label("Age");
        Label h = new Label("Height (in)");
        Label w = new Label("Weight (kg)");
        grid2.add(a, 1, 1);
        grid2.add(h, 1, 2);
        grid2.add(w, 1, 3);

        Text goal = new Text("Purpose for Diet:");
        RadioButton lose = new RadioButton("Lose Weight");
        RadioButton gain = new RadioButton("Gain Weight");
        grid2.add(goal, 2,4);
        grid2.add(lose, 2,5);
        grid2.add(gain, 2,6);

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
    public static void main(String[] args) {
        launch(args);
    }
}