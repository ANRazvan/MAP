package model.statements;

import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public IStmt deepcopy(){
        return new CompStmt(first.deepcopy(),second.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        return second.typecheck(first.typecheck(typeEnv));
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
