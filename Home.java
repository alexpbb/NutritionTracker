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
import javafx.scene.control.Label;
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

    public Home(User user) {
        this.user = user;
    }

    public BorderPane newHome() {
        BorderPane pane = new BorderPane();

        HBox hb = new HBox();
        hb.setSpacing(3);
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setPadding(new Insets(20, 40, 20, 40));

        Text hello = new Text("Hello, " + user.getName());
        hello.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        pane.setTop(hello);

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
        
        TextField food = new TextField();
        food.setPromptText("Enter food");
        TextField serv = new TextField();
        serv.setPromptText("Enter servings");
        TextField cal = new TextField();
        cal.setPromptText("Enter calories");
        Button addFood = new Button("+ADD FOOD");

        hb.getChildren().addAll(food, serv, cal, addFood);
        vb.getChildren().addAll(table, hb);
        pane.setCenter(vb);

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

        addFood.setOnAction (new EventHandler<ActionEvent>() {
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
                } else {
                    Label message = new Label("Enter info.");
                    vb.getChildren().removeIf( node -> GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 7);
                    vb.getChildren().add(message);
                }
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