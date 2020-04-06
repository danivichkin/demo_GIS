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
import java.util.*;

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

            double xPIN = event.getSceneX();
            double yPIN = event.getSceneY();

            if (event.getButton() == MouseButton.PRIMARY) {
                String label = "Tab has pinned: x: " + xPIN + " y:" + yPIN;
                tapLabel.setText(label);
                event.consume();
            }

            if (event.getButton() == MouseButton.SECONDARY) {
                Double[] cords = new Double[]{xPIN, yPIN};
                ArrayList<HashMap<Shape, String>> shapes = kostili();
                for (Map.Entry<Shape, String> entry : shapes.get(0).entrySet()) {
                    System.out.println(entry.getKey().toString());

                    //TODO
                    // Видит только первое значение, ноут сел
                    if (Shape.isDotInSide(entry.getKey(), cords[0], cords[1])) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, entry.getValue());
                            alert.showAndWait();
                    }
                }
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

    private ArrayList<HashMap<Shape, String>> kostili() {
        ArrayList<HashMap<Shape, String>> arrayList = new ArrayList<>();
        HashMap<Shape, String> hashMap = new HashMap();

        Shape.Point point0 = new Shape.Point(76.0, 428.0);
        Shape.Point point1 = new Shape.Point(66.0, 176.0);
        Shape.Point point2 = new Shape.Point(183.0, 213.0);
        Shape.Point point3 = new Shape.Point(251.0, 359.0);
        Shape shapeKazan = new Shape(point0, point1, point2, point3);
        hashMap.put(shapeKazan, "Kazan");

        Shape.Point point10 = new Shape.Point(223, 235);
        Shape.Point point11 = new Shape.Point(224, 190);
        Shape.Point point12 = new Shape.Point(267, 185);
        Shape.Point point13 = new Shape.Point(254, 234);
        Shape shapeDerbishki = new Shape(point0, point1, point2, point3);
        hashMap.put(shapeDerbishki, "Derbishki");

        arrayList.add(hashMap);

        return arrayList;
    }
}
