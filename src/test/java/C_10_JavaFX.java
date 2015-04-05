import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The official Java GUI framework, JavaFX, also got some new features with Java 8.
 */
public class C_10_JavaFX extends Application {

    /*
        Naming:
        Java 7 -> "JavaFX 2.x"
        Java 8 -> "JavaFX 8"
     */


    // new properties and methods annotated with
    // @since JavaFX 8.0

    /**
     * New component: DatePicker (finally!) that uses new Date and Time API
     */
    @Override
    public void start(Stage primaryStage) {
        DatePicker datePicker = new DatePicker();

        Button btn = new Button();
        btn.setText("Do stuff");
        btn.setOnAction(event -> System.out.println(datePicker.getValue()));

        BorderPane root = new BorderPane();
        root.setCenter(datePicker);
        root.setBottom(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
