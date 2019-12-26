package MapElements;

import MapDirections.Vector2d;

public class Fruit {
    private Vector2d position;

    public Fruit(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }
}
