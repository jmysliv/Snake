package MapElements;

import MapDirections.MapDirection;
import MapDirections.TurnDirection;
import MapDirections.Vector2d;
import MapElements.Fruit;
import World.RectangularMap;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Vector2d> body = new ArrayList<>();
    private List<MapDirection> bodyDirections = new ArrayList<>();
    private int length;
    private RectangularMap map;
    private boolean alive;

    public Snake(RectangularMap map){
        this.length = 1;
        this.map = map;
        this.body.add(new Vector2d(map.getRightCorner().x/2, map.getRightCorner().y/2));
        this.bodyDirections.add(MapDirection.NORTH);
        this.alive = true;
    }

    public void turn(TurnDirection turnDirection){
        switch (turnDirection){
            case LEFT:

                this.bodyDirections.set(0, this.bodyDirections.get(0).previous());
                break;
            case RIGHT:

                this.bodyDirections.set(0, this.bodyDirections.get(0).next());
                break;
            default:
                break;
        }
    }

    public void eat(){
        this.length ++;
        this.body.add(this.bodyDirections.get(this.bodyDirections.size() - 1).toUnitVector().oposite().add(this.body.get(this.body.size() - 1)));
        this.bodyDirections.add(this.bodyDirections.get(this.bodyDirections.size() - 1));
        this.map.placeFruit();
    }

    public void move(){
        for(int i=this.body.size() - 1; i>0; i--){
            this.bodyDirections.set(i, this.bodyDirections.get(i-1));
            this.body.set(i, this.body.get(i-1));
        }
        Vector2d newPosition = this.body.get(0).add(this.bodyDirections.get(0).toUnitVector());
        Vector2d size = map.getRightCorner();
        newPosition = new Vector2d((newPosition.x + size.x + 1)%(size.x + 1), (newPosition.y + 1 + size.y)%(size.y + 1));
        if(this.map.canMoveTo(newPosition)) {
            if(map.objectAt(newPosition) instanceof Fruit) eat();
            this.body.set(0, newPosition);
        }
        else {
            this.alive = false;
        }

    }

    public List<Vector2d> getBody(){
        return this.body;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getLength() {
        return length;
    }
}
