package model.statements;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementException, ExpressionException;
    IStmt deepcopy();
    MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException;
}
