package repository;

import model.state.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;

    public Repository() {
        this.prgStates = new ArrayList<>();
    }

    public PrgState getCrtPrg() {
        return prgStates.getFirst();     }

    public void removePrgState() {
        prgStates.removeFirst();
    }

    public void addPrgState(PrgState state) {
        prgStates.add(state);
    }


}