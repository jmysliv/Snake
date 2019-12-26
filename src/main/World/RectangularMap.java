package World;

import MapDirections.TurnDirection;
import MapDirections.Vector2d;
import MapElements.Fruit;
import MapElements.Snake;
import MapElements.Stone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RectangularMap {
    private Vector2d rightCorner;
    private Vector2d leftCorner = new Vector2d(0, 0);
    private Snake snake;
    private Fruit fruit;
    private HashMap<Vector2d, Stone> stones = new HashMap<>();

    public RectangularMap(int size) {
        this.rightCorner = new Vector2d(size - 1, size - 1);
        snake = new Snake(this);
        this.placeFruit();
    }

    public Vector2d getRightCorner() {
        return rightCorner;
    }

    public boolean canMoveTo(Vector2d position){
        if(!(position.follows(leftCorner) && position.precedes(rightCorner))) return false;
        if(isOccupied(position) && !(objectAt(position) instanceof Fruit)) return false;
        return true;
    }

    public boolean isOccupied(Vector2d position){
        if(snake.getBody().contains(position)) return true;
        if(this.fruit != null && fruit.getPosition().equals(position)) return true;
        return this.stones != null && stones.containsKey(position);
    }

    public Object objectAt(Vector2d position){
        if(snake.getBody().contains(position)) return this.snake;
        if(this.fruit != null && fruit.getPosition().equals(position)) return fruit;
        if(this.stones != null) return stones.get(position);
        return null;
    }

    public List<Vector2d> findFreeFields(){
        List<Vector2d> freePlaces = new ArrayList<>();
        for (int i=0; i<this.rightCorner.x; i++){
            for(int j=0; j<this.rightCorner.y; j++) {
                Vector2d tmp = new Vector2d(i, j);
                if (!isOccupied(tmp)) {
                    freePlaces.add(tmp);
                }
            }
        }
        return freePlaces;
    }

    public void placeFruit(){
        List<Vector2d> freePlaces = findFreeFields();
        Random rand = new Random();
        int index = rand.nextInt(freePlaces.size());
        Fruit newFruit = new Fruit(freePlaces.get(index));
        this.fruit = newFruit;
    }

    public void placeStone(){
        List<Vector2d> freePlaces = findFreeFields();
        Random rand = new Random();
        int index = rand.nextInt(freePlaces.size());
        Stone stone = new Stone(freePlaces.get(index));
        this.stones.put(stone.getPosition(), stone);
    }

    public boolean run(TurnDirection turnDirection){
        if(this.snake.isAlive()) {
            this.snake.turn(turnDirection);
            this.snake.move();
            return true;
        }
        return false;
    }

    public int getSnakeLength(){
        return this.snake.getLength();
    }
}
