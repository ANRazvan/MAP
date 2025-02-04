package model.state;
import exceptions.EmptyStackException;
import exceptions.MyException;
import model.adt.*;
import model.statements.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class    PrgState {
    private MyIStack<IStmt> execStack;
//    private MyIMap<String, IValue> symTable;
    private MyIStack<MyIMap<String,IValue>> symTables;
    private MyIList<IValue> outputList;
    private MyIMap<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    private MyILockTable LockTable;
    private MyIProcedureTable procedureTable;
    private static int id = 0;
    private IStmt originalProgram;

    // Static synchronized method to get the next id
    public static synchronized int getNextId() {
        return ++id; // Increment and return the new id
    }

    private final int programId; // Instance-specific id

    public PrgState(IStmt initState, MyIStack<IStmt> execStack, MyIMap<String, IValue> symTable,
                    MyIList<IValue> output, MyIMap<StringValue,BufferedReader> fileTable, MyIHeap heap, MyILockTable locktable, MyIProcedureTable proceduretable) {
        this.execStack = execStack;
        this.symTables = new MySymTableStack();
        this.symTables.push(symTable);
        this.outputList = output;
        execStack.push(initState);
        this.fileTable= fileTable;
        this.heap = heap;
        this.programId = getNextId();
        this.LockTable=locktable;
        this.procedureTable = proceduretable;
        //init();
    }

    public PrgState(IStmt initState, MyIStack<IStmt> execStack, MyIStack<MyIMap<String,IValue>> symTables,
                    MyIList<IValue> output, MyIMap<StringValue,BufferedReader> fileTable, MyIHeap heap, MyILockTable locktable, MyIProcedureTable proceduretable) {
        this.execStack = execStack;
        this.symTables = symTables;
        this.outputList = output;
        execStack.push(initState);
        this.fileTable= fileTable;
        this.heap = heap;
        this.programId = getNextId();
        this.LockTable=locktable;
        this.procedureTable = proceduretable;
        //init();
    }

    public int getProgramId() {
        return programId;
    }

    public Boolean isNotCompleted(){
        return !execStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (execStack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        IStmt currentStmt = execStack.pop();
        System.out.println("One Step Current statement: " + currentStmt);
        return currentStmt.execute(this);
    }


    public MyIStack<IStmt> getExecStack() {
        return execStack;
    }
//    public MyIMap<String, IValue> getSymTable() {
//        return symTable;
//    }

    public MyIMap<String, IValue> getSymTable() {
        return symTables.peek();
    }
    public MyIList<IValue> getOutputList() {
        return outputList;
    }

    public MyIStack<MyIMap<String,IValue>> getAllSymTables() {
        return symTables;
    }

    public MySymTableStack getSymTableStack(){
        return (MySymTableStack) this.symTables;
    }

    @Override
    public String toString() {
        return "Program ID: " + programId + "\n" + execStack.toString() + "\n" + symTables.toString() + "\n"
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


    public Integer getId() {
        return programId;
    }

    public MyILockTable getLockTable() {
        return LockTable;
    }

    public MyIProcedureTable getProcedureTable() {
        return procedureTable;
    }

    public void setLockTable(MyILockTable lockTable) {
        LockTable.setContent(lockTable.getContent());
    }

    public void setProcedureTable(MyIProcedureTable procedureTable) {
        this.procedureTable=procedureTable;
    }
}

