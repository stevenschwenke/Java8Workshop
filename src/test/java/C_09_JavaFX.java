import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

import static javafx.beans.binding.Bindings.when;
import static org.junit.Assert.assertEquals;

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
     * New component: DatePicker (finally!) that uses new Date and Time API.
     * Also: TransformationList -> FilteredList + OrderedList
     */
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(15);

        // DatePicker

        DatePicker datePicker = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        setFormattingToGermanDateFormat(datePicker);
        setSomeForeignChronologies(datePicker);

        Button btn = new Button();
        btn.setText("getValue()");
        // Note that output in the console is default formatting, not formatting of the DatePicker!
        btn.setOnAction(event -> System.out.println(datePicker.getValue()));

        HBox datePickerContainer = new HBox();
        datePickerContainer.setSpacing(10);
        datePickerContainer.getChildren().add(datePicker);
        datePickerContainer.getChildren().add(btn);

        Text datePickerLabelText = new Text("DatePicker");
        datePickerLabelText.setFill(Color.BLUE);
        datePickerLabelText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        TextFlow datePickerLabelTextFlow = new TextFlow(datePickerLabelText);
        root.getChildren().add(new VBox(datePickerLabelTextFlow, datePickerContainer));

        // ListFiltering

        // New TransformationList is a wrapper around a normal list and has two implementations: FilteredList and
        // SortedList. The following is an example for the FilteredList, SortedList is similar.

        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three", "four");
        FilteredList<String> filteredList = new FilteredList<>(list);
        ListView<String> listView = new ListView<>(filteredList);
        TextField textField = new TextField();
        textField.textProperty().addListener((e) -> filteredList.setPredicate((v) -> (v.contains(textField.getText()))));
        Text filteredListLabelText = new Text("ListFiltering");
        filteredListLabelText.setFill(Color.BLUE);
        filteredListLabelText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        TextFlow filteredListLabelTextFlow = new TextFlow(filteredListLabelText);
        VBox listFilteringContainer = new VBox(filteredListLabelTextFlow, textField, listView);

        root.getChildren().add(listFilteringContainer);

        // Task: updateValue

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                long startTime = System.currentTimeMillis();
                while (true) {
                    updateValue(System.currentTimeMillis() - startTime + "");
                    Thread.sleep(1);
                }
            }
        };
        Button button = new Button();
        button.setOnAction((e) -> {
            if (task.isRunning()) {
                task.cancel();
            } else {
                // The executor will execute the task only when it didn't run yet. Additional calls will be ignored.
                Executors.newSingleThreadExecutor().execute(task);
            }
        });
        button.textProperty().bind(when(task.valueProperty().isNotNull()).then(task.valueProperty()).otherwise("Start"));
        Text taskUpdateValueText = new Text("Task.updateValue()");
        taskUpdateValueText.setFill(Color.BLUE);
        taskUpdateValueText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        TextFlow taskUpdateValueTextFlow = new TextFlow(taskUpdateValueText);
        VBox threadContainer = new VBox(taskUpdateValueTextFlow, button);
        root.getChildren().add(threadContainer);

        // new class: Scheduled Service

        ScheduledService<Void> service = new ScheduledService<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        System.out.println("Do something");
                        return null;
                    }
                };
            }
        };
        service.setRestartOnFailure(true);
        service.setPeriod(Duration.seconds(5));
        service.setMaximumFailureCount(10);
        service.setMaximumCumulativePeriod(Duration.minutes(2));
        Button startScheduledService = new Button("Start scheduled service");
        startScheduledService.setOnAction(eventHandler -> service.start());
        Text scheduledServiceText = new Text("ScheduledService");
        scheduledServiceText.setFill(Color.BLUE);
        scheduledServiceText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        TextFlow scheduledServiceTextFlow = new TextFlow(scheduledServiceText);
        root.getChildren().add(new VBox(scheduledServiceTextFlow, startScheduledService));

        // New CSS theme Modena is new default theme!
        Button toggleThemes = new Button();
        SimpleBooleanProperty modena = new SimpleBooleanProperty(true);
        toggleThemes.textProperty().bind(Bindings.when(modena).then("Switch to Caspian").otherwise("Switch to Modena"));
        toggleThemes.setOnAction(eventHandler -> {
            setUserAgentStylesheet(modena.get() ? STYLESHEET_CASPIAN : STYLESHEET_MODENA);
            modena.set(getUserAgentStylesheet().equals(STYLESHEET_MODENA));
        });
        // (If getUserAgendStylesheet() would be a property, the above code would be way smaller.)
        Text toggleThemesText = new Text("toggle themes");
        toggleThemesText.setFill(Color.BLUE);
        toggleThemesText.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        TextFlow toggleThemesTextFlow = new TextFlow(toggleThemesText);
        root.getChildren().add(new VBox(toggleThemesTextFlow, toggleThemes));

        // Setup GUI

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("JavaFX in Java 8");
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


    @Test(expected = Exception.class)
    public void implementationsOfTransformationListAreImmutable() {
        ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three", "four");
        FilteredList<String> filteredList = new FilteredList<>(list);

        // Backing list can be mutated:
        list.remove(0, 1);
        assertEquals(3, list.size());

        // All implementations of TransformationList are immutable, hence changing the list will throw an exception:
        filteredList.add("EXCEPTION!");
    }
}
