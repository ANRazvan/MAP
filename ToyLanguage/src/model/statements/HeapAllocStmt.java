package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocStmt implements IStmt {
    String var;
    IExpression expr;

    public HeapAllocStmt(String var, IExpression expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, KeyNotFoundException, ExpressionException {
        MyIMap<String, IValue> symTable = prgState.getSymTable();
        IValue evalExpr = expr.evaluate(symTable, prgState.getHeap());

        if (!symTable.contains(var)) {
            throw new StatementException("Variable name is not in the symtable.");
        }

        IValue variableValue = symTable.getValue(var);


        if (!(variableValue instanceof RefValue)) {
            throw new StatementException("Variable must be RefType");
        }

        RefValue refValue = (RefValue) variableValue;
        if (!refValue.getLocationType().equals(evalExpr.getType())) {
            throw new StatementException("Wrong type");
        }

        MyIHeap heap = prgState.getHeap();
        int addr = heap.getNextFreeAddr();
        heap.insert(addr, evalExpr);
        symTable.update(var, new RefValue(addr, evalExpr.getType()));

        return prgState;
    }

    @Override
    public IStmt deepcopy() {
        return new HeapAllocStmt(var, expr);
    }

    public String toString(){
        return "new(" + var + ", " + expr.toString() + ")";
    }
}/*
package model.statements;

import exceptions.KeyNotFoundException;
import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.adt.MyIMap;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;

import java.util.HashMap;

public class HeapAllocStmt implements IStmt{
    private String varName;
    private IExpression exp;

    public HeapAllocStmt(String varName, IExpression exp) {
        this.varName = varName;
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new HeapAllocStmt(new String(this.varName), this.exp.deepcopy());
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIMap<String, IValue> symTable = state.getSymTable();
        IValue val = exp.evaluate(symTable, state.getHeap());

        if (!symTable.contains(varName)) {
            throw new KeyNotFoundException("The variable " + varName + " was not found in the symTable");
        }

        IValue varValue = symTable.getValue(varName);

        if(!(varValue instanceof RefValue)) {
            throw new TypeException("The variable " + varName + " is not a reference");
        }

        RefValue refValue = (RefValue) varValue;
        if (!refValue.getLocationType().equals(val.getType())) {
            throw new TypeException("The type of the variable and the type of the expression do not match");
        }

        // print both types
        System.out.println("Types:");
        System.out.println(refValue.getLocationType());
        System.out.println(val.getType());

//        if (!symTable.lookup(varName).getType().equals(val.getType())) {
//            throw new TypeException("The type of the variable and the type of the expression do not match");
//        }
//        if (!val.getType().equals(symTable.lookup(varName).getType())) {
//            throw new TypeException("The type of the variable and the type of the expression do not match");
//        }

        MyIHeap heap = state.getHeap();
        int address = heap.getNextFreeAddr();

        heap.insert(address, val);
        RefValue refVal = new RefValue(address, val.getType());
        symTable.insert(varName, refVal);

        return state;
    }
}
*/