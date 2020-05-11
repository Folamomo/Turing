import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rule {
    private final String symbol;
    private final String replaceWith;
    private final String nextState;
    private final Direction moveTo;

    public static final Rule EMPTY = new Rule("", "", "REJECT", Direction.RIGHT);
    /**
     * @param from Symbolic representation of rule, delimited with /
     *             x/y/r/s means accept state x, replace with x, move right, switch state to s
     */
    public Rule(String from){
        String[] splits = from.split("/");
        if (splits.length < 4) throw new IllegalArgumentException();
        symbol = splits[0];
        replaceWith = splits[1];
        moveTo = switch (splits[2]) {
            case "r","R","Right","right" -> Direction.RIGHT;
            case "l","L","Left","left" -> Direction.LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + splits[2]);
        };
        nextState = splits[3];
    }
}
