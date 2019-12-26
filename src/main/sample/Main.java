package sample;

import MapDirections.TurnDirection;
import MapDirections.Vector2d;
import MapElements.Fruit;
import MapElements.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import World.RectangularMap;


public class Main extends Application {
    TurnDirection turnDirection = TurnDirection.NONE;
    private long animationTimeStep = 100_000_000;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("snake");
        FlowPane root = new FlowPane();
        Pane mapChart = new Pane();
        root.getChildren().addAll( mapChart);
        Scene scene = new Scene(root, 1500, 900);
        RectangularMap map = new RectangularMap(40);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    turnDirection = TurnDirection.RIGHT;
                    break;
                case D:
                    turnDirection = TurnDirection.LEFT;
                    break;
               default:
                   turnDirection = TurnDirection.NONE;
                    break;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                    if (now - lastUpdate >= animationTimeStep) {
                        update(map, mapChart);
                        lastUpdate = now ;
                    }
                }

        };
        timer.start();
        stage.setScene(scene);
        stage.show();
    }

    public void update(RectangularMap map, Pane mapChart) {
        mapChart.getChildren().removeIf(n -> {
            return true;
        });
        map.run(this.turnDirection);
        this.turnDirection = TurnDirection.NONE;
        draw(map, mapChart);
    }


    public void draw(RectangularMap map, Pane mapChart){
        int rectangleSize = 20;
        for(int i=0; i<=map.getRightCorner().x; i++){
            for(int j=0; j<=map.getRightCorner().y; j++){
                Vector2d tmp = new Vector2d(i, j);
                if(map.isOccupied(tmp)) {
                    if (map.objectAt(tmp) instanceof Snake) {
                        Rectangle rectangle = new Rectangle(rectangleSize, rectangleSize);
                        rectangle.setFill(Color.GREEN);
                        rectangle.setX(rectangleSize * i);
                        rectangle.setY(rectangleSize * j);
                        mapChart.getChildren().add(rectangle);
                    } else if (map.objectAt(tmp) instanceof Fruit) {
                        Rectangle rectangle = new Rectangle(rectangleSize, rectangleSize, Color.RED);
                        rectangle.setX(rectangleSize * i);
                        rectangle.setY(rectangleSize * j);
                        mapChart.getChildren().add(rectangle);
                    }
                }
                else {
                    Rectangle rectangle = new Rectangle(rectangleSize, rectangleSize, Color.TRANSPARENT);
                    rectangle.setX(rectangleSize*i);
                    rectangle.setY(rectangleSize*j);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(1);
                    mapChart.getChildren().add(rectangle);
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
