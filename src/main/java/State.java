import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.function.Function.*;

public class State {
    private Map<String, Rule> rules;
    @Getter
    private final String name;
    public static final State ACCEPT = new State("ACCEPT");
    public static final State REJECT = new State("REJECT");

    public static State fromTMDL(String tmdl){
        String [] splits = tmdl.split(":");
        State result = new State(splits[0]);
        result.addRules(
                Arrays.stream(splits[1].replaceAll(" ", "").split(","))
                .map(Rule::new)
                .collect(Collectors.toList()));
        return result;
    }

    public State(String name) {
        this.name = name;
        rules = new HashMap<>();
    }

    public void addRule(Rule rule){
        rules.put(rule.getSymbol(), rule);
    }

    public void addRules(Collection<Rule> newRules){
        rules.putAll(newRules.stream().collect(Collectors.toMap(Rule::getSymbol, identity())));
    }

    public Rule getRule(String symbol){
        return rules.getOrDefault(symbol, Rule.EMPTY);
    }


}
