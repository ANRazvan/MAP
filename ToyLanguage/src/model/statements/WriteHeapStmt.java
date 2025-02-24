package model.statements;

import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
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

       IValue value = symTable.lookup(varName);
       System.out.println(varName);
       if(!(value instanceof RefValue)) {
           System.out.println(value);
           throw new TypeException("Variable is not a ref value");
       }
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

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new TypeException("WriteHeap: right hand side and left hand side have different types");
    }

}
