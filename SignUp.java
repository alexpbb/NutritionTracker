import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

        GridPane grid2 = new GridPane();



        Text welcome = new Text("WELCOME");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        grid.add(welcome, 1, 0);
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

        Scene scene1 = new Scene(grid2, 500, 450);

        submit.setOnAction (new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) throws IllegalArgumentException {
                if (fNameField != null && lNameField != null && !fNameField.getText().isEmpty() && !lNameField.getText().isEmpty()) {
                    String fName = fNameField.getText();
                    String lname = lNameField.getText();
                    primaryStage.setScene(scene1);
                    primaryStage.show();

                } else {
                    Label message = new Label("Invalid input.");
                    grid.add(message, 1, 4);
                }
            }
        });

    }

    // if (sex.equals("Female")) {
    //     this.weight = 117 + (height - 60) * 2.9;
    // }
    // this.weight = 124 + (height - 60) * 3.1;

    public static void main(String[] args) {
        launch(args);
    }

}