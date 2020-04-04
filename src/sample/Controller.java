package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    double xPIN = 0;
    double yPIN = 0;

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
    void plusButton(ActionEvent event) throws IOException {
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
            String label = "Tab has pinned: x: " + xPin + " y:" + yPin;
            tapLabel.setText(label);

            event.consume();
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

}
