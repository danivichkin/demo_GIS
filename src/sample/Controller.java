package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    void minusButton(ActionEvent event) {
        double height = ImageView.getFitHeight();
        double width = ImageView.getFitWidth();
        ImageView.setFitHeight(height - 100);
        ImageView.setFitWidth(width - 100);
    }

    @FXML
    void plusButton(ActionEvent event) throws IOException {
        double height = ImageView.getFitHeight();
        double width = ImageView.getFitWidth();
        ImageView.setFitHeight(height + 100);
        ImageView.setFitWidth(width + 100);


        //TODO
        Rectangle2D rectangle2D = new Rectangle2D(800,800,800,800);
        ImageView.setViewport(rectangle2D);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> langs = FXCollections.observableArrayList("1");
        choiceBoxMap.setItems(langs);
        choiceBoxMap.setValue("1");

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

    private void getAllMaps() {
        File dir = new File("src/resources/maps/");
        File[] maps = dir.listFiles();
        System.out.println(Arrays.toString(maps));
    }


}
