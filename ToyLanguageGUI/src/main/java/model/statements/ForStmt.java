package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;

public class ForStmt implements IStmt {
    private String variable;
    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;
    private IStmt stmt;

    public ForStmt(String variable,IExpression exp1, IExpression exp2, IExpression exp3, IStmt stmt) {
        this.variable = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IStmt newstmt = new CompStmt(new VarDeclStmt(variable, new IntType()), new CompStmt(new AssignStmt(variable, exp1), new WhileStmt(new RelationalExp(new VarExp(variable),"<",exp2), new CompStmt(stmt, new AssignStmt(variable, exp3)))));
        state.getExecStack().push(newstmt);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new ForStmt(variable, exp1.deepcopy(), exp2.deepcopy(), exp3.deepcopy(), stmt.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IStmt vardeclstmt = new VarDeclStmt(variable, new IntType());
        vardeclstmt.typecheck(typeEnv);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);
        if(!type1.equals(new IntType()))
            throw new StatementException("First expression is not an integer");
        if(!type2.equals(new IntType()))
            throw new StatementException("Second expression is not an integer");
        if(!type3.equals(new IntType()))
            throw new StatementException("Third expression is not an integer");
        return typeEnv;
    }

    public String toString()
    {
        return "for(v=" + exp1.toString() + "; " + exp2.toString() + "; " + exp3.toString() + "){" + stmt.toString() + "}";
    }
}

