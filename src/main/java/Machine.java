import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.*;

public class Machine {
    private Map<String, State> states;
    private State current;
    private Tape tape;

    /**
     * @param tmdl Turing Machine Description Language
     *             First lane represents the tape
     *             Second lane contains name of starting symbol
     *             Next lanes are states with rules formatted as:
     *             Name: rule, rule, ...
     *             Rules in format read/write/moveTo/nextState
     *
     * @return new Machine form tmdl
     * @throws IOException if reader fails
     */
    public static Machine fromTMDL(String tmdl) throws IOException {
        return fromTMDL(new BufferedReader(new StringReader(tmdl)));
    }

    public static Machine fromTMDL(BufferedReader reader) throws IOException {
        String tapeString = reader.readLine();
        String start = reader.readLine();
        List<State> stateList = reader.lines().map(State::fromTMDL).collect(Collectors.toList());
        return new Machine(tapeString, start, stateList);
    }


    public Machine(String tape, String start, State... states){
        this(tape, start, Arrays.asList(states));
    }

    public Machine(String tape, String start, Collection<State> states){
        this.tape = new Tape(tape);
        this.states = states.stream().collect(Collectors.toMap(State::getName, identity()));
        this.states.put("ACCEPT", State.ACCEPT);
        this.states.put("REJECT", State.REJECT);
        current = this.states.get(start);
        if (current == null) throw new IllegalStateException("No such state: " + start);
    }

    public void run(){
        while (current!=State.ACCEPT && current!=State.REJECT){
            step();
        }
    }

    public void step(){
        Rule rule = current.getRule(tape.get());
        current = states.get(rule.getNextState());
        if (current == null) throw new IllegalStateException("No such state: " + rule.getNextState());
        if (current == State.ACCEPT) {
            System.out.println("Tape accepted");
            return;
        }
        if (current == State.REJECT) {
            System.out.println("Tape rejected");
            return;
        }
        tape.set(rule.getSymbol());
        tape.move(rule.getMoveTo());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Machine.class.getResourceAsStream("test.tmdl")));
        Machine machine = fromTMDL(reader);
        machine.run();
    }
}
