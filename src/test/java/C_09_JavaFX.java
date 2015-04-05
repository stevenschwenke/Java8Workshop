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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;

/**
 * The official Java GUI framework, JavaFX, also got some new features with Java 8.
 */
public class C_09_JavaFX extends Application {

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

        datePicker.setShowWeekNumbers(true);

        setFormattingToGermanDateFormat(datePicker);

        setSomeForeignChronologies(datePicker);

        Button btn = new Button();
        btn.setText("getValue()");
        // Note that output in the console is default formatting, not formatting of the DatePicker!
        btn.setOnAction(event -> System.out.println(datePicker.getValue()));

        BorderPane root = new BorderPane();
        root.setCenter(datePicker);
        root.setBottom(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Sets the formatting of a {@link javafx.scene.control.DatePicker} to the german date format "dd.MM.yyyy".
     *
     * @param datePicker to set format for
     */
    private void setFormattingToGermanDateFormat(DatePicker datePicker) {
        // Convert date in text field manually:
        String pattern = "dd.MM.yyyy";
        datePicker.setPromptText(pattern.toLowerCase());

        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Shows the support of {@link javafx.scene.control.DatePicker} for different cultural time systems.
     *
     * @param datePicker to set chronology for
     */
    private void setSomeForeignChronologies(DatePicker datePicker) {
        // Japanese calendar.
//        datePicker.setChronology(JapaneseChronology.INSTANCE);

        // Hijrah calendar.
//        datePicker.setChronology(HijrahChronology.INSTANCE);

        // Minguo calendar.
//        datePicker.setChronology(MinguoChronology.INSTANCE);

        // Buddhist calendar.
//        datePicker.setChronology(ThaiBuddhistChronology.INSTANCE);
    }
}
