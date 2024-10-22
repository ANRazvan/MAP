package model.state;
import model.adt.MyStack;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.statements.IStatement;
import model.value.IValue;

public class PrgState {
    private MyStack<IStatement> execStack;
    private MyDictionary<String, IValue>  symTable;
    private MyList<String> outputList;

    public PrgState(IStatement initState, MyStack<IStatement> execStack, MyDictionary<String, IValue> symTable) {
        this.execStack = execStack;
        this.symTable = symTable;
        outputList = new MyList<>();
        execStack.push(initState);
    }

    public MyStack<IStatement> getExecStack() {
        return execStack;
    }
    public MyDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyList<String> getOutputList() {
        return outputList;
    }

    @Override
    public String toString() {
        return execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n";
    }
}
