package model.statements;

import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private IExpression expression;

    public CloseRFileStmt(IExpression expression){
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) {
        IValue value = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new StatementException("The result of the expression is not a StringType");

        StringValue fileName = (StringValue)value;
        if(!prgState.getFileTable().contains(fileName))
            throw new StatementException("The file was not found");

        try{
            prgState.getFileTable().getValue(fileName).close();
            prgState.getFileTable().remove(fileName);
            return prgState;
        }catch(IOException e){
            throw new StatementException("Problem at closing the BufferedReader");
        }


    }

    @Override
    public IStmt deepcopy() {
        return new CloseRFileStmt(this.expression.deepcopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.expression.toString() + ");";
    }
}