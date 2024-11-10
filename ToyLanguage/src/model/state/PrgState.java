package model.state;
import model.adt.*;
import model.statements.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.util.Dictionary;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> execStack;
    private MyIDictionary<String, IValue>  symTable;
    private MyIList<IValue> outputList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(IStmt initState, MyIStack<IStmt> execStack, MyIDictionary<String, IValue> symTable,
                    MyIList<IValue> output, MyIDictionary<StringValue,BufferedReader> fileTable) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = output;
        execStack.push(initState);
        this.fileTable= fileTable;
        //init();
    }

    public MyIStack<IStmt> getExecStack() {
        return execStack;
    }
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIList<IValue> getOutputList() {
        return outputList;
    }

    public void init(){
        this.execStack = new MyStack<IStmt>();
        this.symTable = new MyDictionary<String,IValue>();
        this.outputList = new MyList<IValue>();
    }

    @Override
    public String toString() {
        return execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n" + fileTableToString() + "\n";
    }

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable: \n");
        for(StringValue key : this.fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
}
