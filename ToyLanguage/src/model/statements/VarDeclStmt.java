package model.statements;

import exceptions.StatementException;
import model.state.PrgState;
import model.type.IType;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException{
        if(state.getSymTable().contains(this.name)) throw new StatementException("A variable with the same name already exists!\n");
        state.getSymTable().insert(this.name, this.type.getDefaultValue());
        return state;
    }

    public IStmt deepcopy(){
        return new VarDeclStmt(new String(name),type.deepcopy());
    }

    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
