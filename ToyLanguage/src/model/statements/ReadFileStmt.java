package model.statements;

import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import model.type.IntType;
import model.type.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private IExpression expression;
    private String varName;

    public ReadFileStmt(IExpression expression, String varName){
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prgState)  {

        MyIMap<String, IValue> symTable = prgState.getSymTable();

        if (!symTable.contains(this.varName))
            throw new StatementException("Variable not found");

        IValue value = symTable.getValue(this.varName);
        if (!value.getType().equals(new IntType()))
            throw new StatementException("The variable is not a String type");

        IValue result = this.expression.evaluate(symTable, prgState.getHeap());
        if (!result.getType().equals(new StringType()))
            throw new StatementException("The result of the expression is not a StringType");

        if (!prgState.getFileTable().contains((StringValue) result))
            throw new StatementException("There is no BufferedReader for that String in the fileTable");

        BufferedReader reader = prgState.getFileTable().getValue((StringValue) result);

        try {
            String line = reader.readLine();
            if (line == null)
                line = "0";

            int parser = Integer.parseInt(line);

            symTable.insert(this.varName, new IntValue(parser));
            return prgState;

        } catch (IOException e) {
            throw new StatementException("Problem at reading from the BufferedReader");
        }
    }

    @Override
    public IStmt deepcopy(){
       return new ReadFileStmt(this.expression.deepcopy(),new String(this.varName));
    }

    @Override
    public String toString(){
        return "readFile(" + this.expression.toString() + ", " + this.varName + ")";
    }


}
