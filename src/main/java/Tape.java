import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Tape {
    private ArrayDeque<String> data;
    private int location;
    private String leftInfiniteSymbol;
    private String rightInfiniteSymbol;
    public Tape(String init){
        data = init.chars().mapToObj(Character::toString).collect(Collectors.toCollection(ArrayDeque::new));
        location = 0;
        leftInfiniteSymbol = data.getFirst();
        rightInfiniteSymbol = data.getLast();
    }
    public String get(){
        return data.getFirst();
    }

    public void move(Direction direction){
        switch (direction) {
            case RIGHT -> moveRight();
            case LEFT  -> moveLeft();
        }
    }

    public void moveRight(){
        ++location;
        data.addLast(data.removeFirst());
        if (location>=data.size()){
            data.addFirst(rightInfiniteSymbol);
        }
    }
    public void moveLeft(){
        --location;
        data.addFirst(data.removeLast());
        if(location < 0){
            data.addFirst(leftInfiniteSymbol);
        }
    }
    public void set(String symbol){
        data.removeFirst();
        data.addFirst(symbol);
    }
    public boolean is(String symbol){
        return data.getFirst().equals(symbol);
    }
}
