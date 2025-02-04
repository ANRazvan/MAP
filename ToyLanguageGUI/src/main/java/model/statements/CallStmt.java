package model.statements;

import exceptions.InterpreterException;
import javafx.util.Pair;
import model.adt.MyIMap;
import model.adt.MyList;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;

import java.util.List;
import java.util.Vector;

import model.type.IType;

public class CallStmt implements IStmt {
    private String functionName;
    private MyList<IExpression> parameters;

    public CallStmt(String functionName, List<IExpression> parameters) {
        this.functionName = functionName;
        this.parameters = new MyList<IExpression>();

        for (int i = 0; i < parameters.size(); ++i) {
            this.parameters.add(parameters.get(i));
        }
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        try {
            Pair<List<String>, IStmt> functionEntry = state.getProcedureTable().lookup(functionName);
            if (functionEntry == null)
                throw new InterpreterException(String.format("Function '%s' does not exist!", functionName));

            List<String> paramNames = functionEntry.getKey();
            IStmt functionBody = functionEntry.getValue();

            List<IValue> paramValues = new Vector<IValue>();
            for (int i = 0; i < parameters.size(); ++i)
                paramValues.add(parameters.get(i).evaluate(state.getSymTable(), state.getHeap()));

            MyIMap<String, IValue> newSymbolsTable = new MyMap<>();
            int size = paramNames.size();
            for (int i = 0; i < size; ++i)
                newSymbolsTable.insert(paramNames.get(i), paramValues.get(i));

            state.getAllSymTables().push(newSymbolsTable);
            state.getExecStack().push(new ReturnStmt());
            state.getExecStack().push(functionBody);
        } catch (InterpreterException e) {
            throw new InterpreterException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        List<IExpression> newParams = new Vector<IExpression>();
        for (int i = 0; i < parameters.size(); ++i) {
            try {
                newParams.add(parameters.get(i).deepcopy());
            } catch (InterpreterException e) {
                return null;
            }
        }

        return new CallStmt(functionName, newParams);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("call " + functionName + "(");
        for (int i = 0; i < parameters.size() - 1; ++i) {
            try {
                result.append(parameters.get(i).toString()).append(", ");
            } catch (InterpreterException e) {
                return null;
            }
        }

        if (parameters.size() > 0) {
            try {
                result.append(parameters.get(parameters.size() - 1).toString());
                result.append(")");
            } catch (InterpreterException e) {
                return null;
            }
        }

        return result.toString();
    }


}
