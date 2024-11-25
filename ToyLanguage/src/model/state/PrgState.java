package model.state;
import model.adt.*;
import model.statements.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> execStack;
    private MyIMap<String, IValue> symTable;
    private MyIList<IValue> outputList;
    private MyIMap<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;

    public PrgState(IStmt initState, MyIStack<IStmt> execStack, MyIMap<String, IValue> symTable,
                    MyIList<IValue> output, MyIMap<StringValue,BufferedReader> fileTable, MyIHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = output;
        execStack.push(initState);
        this.fileTable= fileTable;
        this.heap = heap;
        //init();
    }

    public MyIStack<IStmt> getExecStack() {
        return execStack;
    }
    public MyIMap<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIList<IValue> getOutputList() {
        return outputList;
    }

    public void init(){
        this.execStack = new MyStack<IStmt>();
        this.symTable = new MyMap<String,IValue>();
        this.outputList = new MyList<IValue>();
    }

    @Override
    public String toString() {
        return execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n" + fileTableToString() + "\n" + heap.toString() + "\n";
    }

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable: \n");
        for(StringValue key : this.fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    public MyIMap<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeap() {
        return this.heap;
    }
}
