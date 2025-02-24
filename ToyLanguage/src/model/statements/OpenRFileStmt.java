package model.statements;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    IExpression expression;

    public OpenRFileStmt(IExpression expression){
        this.expression = expression;
    }

    public PrgState execute(PrgState prgState){
        IValue val = expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        if(!val.getType().equals(new StringType()))
            throw new StatementException("The expression is not a string");

        StringValue fileName = (StringValue) val;
        if(prgState.getFileTable().contains(fileName))
            throw new StatementException("The file is already open");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName.getValue()));
            prgState.getFileTable().insert(fileName, reader);
            return null;
        }
        catch(IOException e){
            throw new StatementException("Problem at opening the file");
        }

    }

    @Override
    public IStmt deepcopy() {
        return new OpenRFileStmt(expression.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IType type = expression.typecheck(typeEnv);
        if(type.equals(new StringType()))
            return typeEnv;
        else
            throw new StatementException("The expression is not a string");
    }

    public String toString(){
        return "openRFile(" + expression.toString() + ")";
    }


}
