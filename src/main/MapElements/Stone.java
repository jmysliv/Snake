package MapElements;

import MapDirections.Vector2d;

public class Stone {
    private Vector2d position;

    public Stone(Vector2d position){
        this.position = position;
    }
    public Vector2d getPosition() {
        return position;
    }
}
