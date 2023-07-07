import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Home extends Application{
    private User user;

    public Home(User user) {
        this.user = user;
    }

    public BorderPane newHome() {
        BorderPane pane = new BorderPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        //pane.setCenter(grid);

        HBox hb = new HBox();
        hb.setSpacing(3);
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setPadding(new Insets(10, 10, 10, 10));

        Text hello = new Text("Hello, " + user.getName());
        hello.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        pane.setTop(hello);

        TableView<Food> table = new TableView<Food>();
        table.setEditable(true);
        TableColumn col = new TableColumn("Food");
        col.setMinWidth(150);
        TableColumn col2 = new TableColumn("Servings");
        TableColumn col3 = new TableColumn("Calories");
        table.getColumns().addAll(col, col2, col3);
        // grid.add(table, 0, 0);
        
        TextField food = new TextField();
        food.setPromptText("Enter food");
        TextField serv = new TextField();
        serv.setPromptText("Enter servings");
        TextField cal = new TextField();
        cal.setPromptText("Enter calories");
        // grid.add(food, 0, 2);
        // grid.add(serv, 1, 2);
        // grid.add(cal, 2, 2);

        Button addFood = new Button("+ADD FOOD");
        // grid.add(addFood, 3, 2);

        hb.getChildren().addAll(food, serv, cal, addFood);
        vb.getChildren().addAll(table, hb);
        pane.setCenter(vb);

        addFood.setOnAction (new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) throws IllegalArgumentException {
                System.out.println(1); 
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