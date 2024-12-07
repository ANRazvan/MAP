package model.statements;

import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;

public class NopStmt implements IStmt {
    public NopStmt() {};

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    public IStmt deepcopy(){
        return new NopStmt();
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
