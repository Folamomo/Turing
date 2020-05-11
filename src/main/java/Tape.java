import java.util.List;
import java.util.stream.Collectors;

public class Tape {
    private List<String> data;
    private int location;
    public Tape(String init){
        data = init.chars().mapToObj(Character::toString).collect(Collectors.toList());
    }
    public String get(){
        return data.get(location);
    }

    public void move(Direction direction){
        switch (direction) {
            case RIGHT -> moveRight();
            case LEFT  -> moveLeft();
        }
    }

    public void moveRight(){
        ++location;
    }
    public void moveLeft(){
        --location;
    }
    public void set(String symbol){
        data.set(location, symbol);
    }
    public boolean is(String symbol){
        return data.get(location).equals(symbol);
    }
}
