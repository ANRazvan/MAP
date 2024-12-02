package model.statements;

import model.state.PrgState;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public IStmt deepcopy(){
        return new CompStmt(first.deepcopy(),second.deepcopy());
    }

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public PrgState execute(PrgState state) {
        state.getExecStack().push(second);
        state.getExecStack().push(first);
        return null;
    }

    public String toString() {
        return first.toString() + ";" + second.toString();
    }
}
