package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.expressions.RelationalExp;
import model.state.PrgState;
import model.type.IType;

public class SwitchStatement implements IStmt {
    private IExpression exp;
    private IExpression exp1;
    private IStmt stmt1;
    private IExpression exp2;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStatement(IExpression exp, IExpression exp1, IStmt stmt1, IExpression exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IExpression expr1 = new RelationalExp(exp,"=", exp1);
        IExpression expr2 = new RelationalExp(exp, "=", exp2);
        IStmt doubleif = new IfStmt(expr1, stmt1, new IfStmt(expr2, stmt2, stmt3));
        state.getExecStack().push(doubleif);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new SwitchStatement(exp.deepcopy(), exp1.deepcopy(), stmt1.deepcopy(), exp2.deepcopy(), stmt2.deepcopy(), stmt3.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IType type1 = exp.typecheck(typeEnv);
        IType type2 = exp1.typecheck(typeEnv);
        IType type3 = exp2.typecheck(typeEnv);
        if(type1.equals(type2) && type1.equals(type3))
        {
            stmt1.typecheck(typeEnv.clone());
            stmt2.typecheck(typeEnv.clone());
            stmt3.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new StatementException("The expressions are not of the same type");
    }

    @Override
    public String toString() {
        return "switch(" + exp.toString() + ") case " + exp1.toString() + ": " + stmt1.toString() + " case " + exp2.toString() + ": " + stmt2.toString() + " default: " + stmt3.toString();
    }
}


