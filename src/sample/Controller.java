package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxMap;

    @FXML
    private ImageView ImageView;

    @FXML
    private Button plusButton;

    @FXML
    private Button minusButton;

    @FXML
    private Label xCord;

    @FXML
    private Label yCord;

    @FXML
    private Label tapLabel;

    @FXML
    private Button changeTheMapButton;

    @FXML
    void changeTheMapButton(ActionEvent event) {
        if (choiceBoxMap.getValue().equals(getTheNameOfCurrentImage())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выбрана текущая карта");
            alert.showAndWait();
        } else {
            File file = new File("src/resources/maps/" + choiceBoxMap.getValue());
            Image image = new Image(file.toURI().toString());
            ImageView.setImage(image);
        }
    }

    @FXML
    void minusButton(ActionEvent event) {
        double height = ImageView.getFitHeight();
        double width = ImageView.getFitWidth();

        if (height >= 850 && width >= 820) {
            ImageView.setFitHeight(height - 100);
            ImageView.setFitWidth(width - 100);
        }
    }

    @FXML
    void plusButton(ActionEvent event) {
        double height = ImageView.getFitHeight();
        double width = ImageView.getFitWidth();

        ImageView.setFitHeight(height + 100);
        ImageView.setFitWidth(width + 100);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> langs = getAllMaps();
        choiceBoxMap.setItems(langs);
        choiceBoxMap.setValue(getAllMaps().get(0));

        ImageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {

            double xPin = event.getSceneX();
            double yPin = event.getSceneY();

            if (event.getButton() == MouseButton.PRIMARY) {
                String label = "Tab has pinned: x: " + xPin + " y:" + yPin;
                tapLabel.setText(label);
                event.consume();
            }

            if (event.getButton() == MouseButton.SECONDARY) {
                Double[] doubles = new Double[]{xPin, yPin};
                System.out.println("Eee");
            }
        });

        ImageView.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            xCord.setText(String.valueOf(mouseEvent.getSceneX()));
            yCord.setText(String.valueOf(mouseEvent.getSceneY()));
        });

    }

    private ObservableList<String> getAllMaps() {
        ObservableList<String> strings = FXCollections.observableArrayList();
        File dir = new File("src/resources/maps/");
        String[] maps = dir.list();

        if (maps != null) {
            strings.addAll(Arrays.asList(maps));
        }
        return strings;
    }

    private String getTheNameOfCurrentImage() {
        String[] fullUrl = ImageView.getImage().getUrl().split("/");
        return (fullUrl[fullUrl.length - 1]);
    }


    private HashMap<Shape, String> mapsObjects() {
        HashMap<Shape, String> hashMap = new HashMap<>();
        Shape.Point point = new Shape.Point(76.0, 428.0);
        Shape.Point point1 = new Shape.Point(66.0, 176.0);
        Shape.Point point2 = new Shape.Point(244.0, 192.0);
        Shape.Point point3 = new Shape.Point(296.0, 424.0);
        Shape shape = new Shape(point, point1, point2, point3);
        hashMap.put(shape, "Kazan");
        return hashMap;
    }


}
