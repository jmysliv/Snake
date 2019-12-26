package MapDirections;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;


    @Override
    public String toString(){
        switch (this){
            case NORTH:
                return "N";
            case EAST:
                return "E";
            case SOUTH:
                return "S";
            case WEST:
                return "W";
            default:
                return "Error";
        }
    }
    public MapDirection next(){
        switch (this){
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return NORTH;
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                return NORTH;
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH:
                return new Vector2d(0, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTH:
                return new Vector2d(0, -1);
            case WEST:
                return new Vector2d(-1, 0);
            default:
                return new Vector2d(0, 0);
        }
    }

}
