package sample;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    double xPIN = 0;
    double yPIN = 0;
    int scale = 1; // костыль

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
            scale = 1;
        }
    }

    @FXML
    void up(ActionEvent event) {
        double height = ImageView.getImage().getHeight();
        double width = ImageView.getImage().getWidth();
        Double[] centerPoint = getCenterOfTheImageView(ImageView);
        ImageView.setViewport(new Rectangle2D(centerPoint[0], centerPoint[1] - 100, height, width));
    }

    @FXML
    void right(ActionEvent event) {
        double height = ImageView.getImage().getHeight();
        double width = ImageView.getImage().getWidth();
        Double[] centerPoint = getCenterOfTheImageView(ImageView);
        ImageView.setViewport(new Rectangle2D(centerPoint[0] + 100, centerPoint[1], height, width));
    }

    @FXML
    void down(ActionEvent event) {
        double height = ImageView.getImage().getHeight();
        double width = ImageView.getImage().getWidth();
        Double[] centerPoint = getCenterOfTheImageView(ImageView);
        ImageView.setViewport(new Rectangle2D(centerPoint[0], centerPoint[1] + 100, height, width));
    }

    @FXML
    void left(ActionEvent event) {
        double height = ImageView.getImage().getHeight();
        double width = ImageView.getImage().getWidth();
        Double[] centerPoint = getCenterOfTheImageView(ImageView);
        ImageView.setViewport(new Rectangle2D(centerPoint[0] - 100, centerPoint[1], height, width));
    }

    @FXML
    void minusButton(ActionEvent event) {
        if (scale > 0) {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), ImageView);
            scaleTransition.setByX(-0.1f);
            scaleTransition.setByY(-0.1f);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
            scale -= 1;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Максимальное отдальне");
            alert.showAndWait();
        }
    }

    @FXML
    void plusButton(ActionEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), ImageView);
        scaleTransition.setByX(0.1f);
        scaleTransition.setByY(0.1f);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        scale++;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> langs = getAllMaps();
        choiceBoxMap.setItems(langs);
        choiceBoxMap.setValue(getAllMaps().get(0));

        ImageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {

            xPIN = event.getSceneX();
            yPIN = event.getSceneY();

            if (event.getButton() == MouseButton.PRIMARY) {
                String label = "Tab has pinned: x: " + xPIN + " y:" + yPIN;
                tapLabel.setText(label);
                event.consume();
            }

            if (event.getButton() == MouseButton.SECONDARY) {
                Double[] cords = new Double[]{xPIN, yPIN};
                ArrayList<Shape.ObjectOfShape> shapes = kostili();
                for (int i = 0; i < shapes.size(); i++) {
                    if (Shape.isDotInSide(shapes.get(i).getShape(), cords[0], cords[1])) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, shapes.get(i).getName());
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

    private Double[] getCenterOfTheImageView(ImageView imageView) {
        double x = imageView.getViewport().getMinX();
        double y = imageView.getViewport().getMinY();
        return new Double[]{x, y};
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

    private ArrayList<Shape.ObjectOfShape> kostili() {
        ArrayList<Shape.ObjectOfShape> arrayList = new ArrayList<>();

        Shape.Point point0 = new Shape.Point(76.0, 428.0);
        Shape.Point point1 = new Shape.Point(66.0, 176.0);
        Shape.Point point2 = new Shape.Point(183.0, 213.0);
        Shape.Point point3 = new Shape.Point(251.0, 359.0);
        Shape shapeKazan = new Shape(point0, point1, point2, point3);
        Shape.ObjectOfShape objectOfShapeKazan = new Shape.ObjectOfShape(shapeKazan, "Kazan");
        arrayList.add(objectOfShapeKazan);

        Shape.Point point10 = new Shape.Point(223, 235);
        Shape.Point point11 = new Shape.Point(224, 190);
        Shape.Point point12 = new Shape.Point(267, 185);
        Shape.Point point13 = new Shape.Point(254, 234);
        Shape shapeDerbishki = new Shape(point10, point11, point12, point13);
        Shape.ObjectOfShape objectOfShapeDerbishki = new Shape.ObjectOfShape(shapeDerbishki, "Derbishki");
        arrayList.add(objectOfShapeDerbishki);

        return arrayList;
    }
}
