package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.expressions.NegExp;
import model.state.PrgState;
import model.type.IType;

public class RepeatUntilStmt implements IStmt{
    private IStmt stmt;
    private IExpression cond;

    public RepeatUntilStmt(IStmt stmt, IExpression cond){
        this.stmt = stmt;
        this.cond = cond;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        state.getExecStack().push(new CompStmt(stmt, new WhileStmt(new NegExp(cond), stmt)));
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new RepeatUntilStmt(stmt.deepcopy(), cond.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        cond.typecheck(typeEnv);
        stmt.typecheck(typeEnv.clone());
        return typeEnv;
    }

    public String toString() {
        return "repeat {" + stmt.toString() + "} until (" + cond.toString() + ")";
    }

}
