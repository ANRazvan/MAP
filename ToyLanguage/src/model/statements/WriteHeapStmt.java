package model.statements;

import exceptions.KeyNotFoundException;
import exceptions.TypeException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStmt implements IStmt{
    private String varName;
    private IExpression exp;

    public WriteHeapStmt(String var_name, IExpression exp) {
        this.varName = var_name;
        this.exp = exp;
    }

    public String toString() {
        return "WriteHeap(" + varName + "," + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) {
       MyIMap<String, IValue> symTable = state.getSymTable();
       MyIHeap heap = state.getHeap();
       if(!symTable.contains(varName))
           throw new KeyNotFoundException("Variable not found in symbol table");

       IValue value = symTable.getValue(varName);
       if(!(value instanceof RefValue))
           throw new TypeException("Variable is not a ref value");

       RefValue refValue = (RefValue) value;
       if(!heap.containsAddr(refValue.getAddr()))
           throw new KeyNotFoundException("Address not found in heap");

       IValue expValue = exp.evaluate(symTable, heap);
       if(!expValue.getType().equals(refValue.getLocationType()))
           throw new TypeException("Types do not match");
       heap.update(refValue.getAddr(), expValue);
       return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WriteHeapStmt(varName, exp.deepcopy());
    }

}
