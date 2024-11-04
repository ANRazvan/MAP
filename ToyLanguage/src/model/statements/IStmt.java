package model.statements;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementException, ExpressionException;
    IStmt deepcopy();
}
