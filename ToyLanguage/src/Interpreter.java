import controller.Controller;
import model.adt.MyHeap;
import model.adt.MyMap;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        System.out.println("Enter the path: \n");
        String logFilePath;
        Scanner scanner1 = new Scanner(System.in);
        logFilePath = scanner1.next();
        /*        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        MyStack<IStmt> st = new MyStack<>();
        MyMap<String, IValue> sym = new MyMap<>();
        MyMap<StringValue, BufferedReader> fileTable = new MyMap<>();
        MyHeap heap1 = new MyHeap();
        PrgState prg1 = new PrgState(ex1, st, sym,new MyList<IValue>(),fileTable,heap1);

        IRepository repo1 = new Repository(prg1,path);
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        MyStack<IStmt> st2 = new MyStack<>();
        MyMap<String, IValue> sym2 = new MyMap<>();
        MyMap<StringValue, BufferedReader> fileTable2 = new MyMap<>();
        MyHeap heap2 = new MyHeap();
        PrgState prg2 = new PrgState(ex2, st2, sym2,new MyList<IValue>(),fileTable2,heap2);


        IRepository repo2 = new Repository(prg2,path);
        Controller ctr2 = new Controller(repo2);


*/
        /*        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(false))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));*/
        /*


        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new IntType()),new CompStmt(new VarDeclStmt("b",new IntType()),new CompStmt (new AssignStmt("a",new ValueExp(new IntValue(2))),
                new CompStmt(new AssignStmt("b",new ValueExp(new IntValue(3))),new CompStmt(new IfStmt(new RelationalExp(new VarExp("a"),"<",new VarExp("b")),
                        new AssignStmt("a",new ValueExp(new IntValue(3))),new AssignStmt("b",new ValueExp(new IntValue(4))))
                ,new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))))))));

        MyStack<IStmt> st3 = new MyStack<IStmt>();
        MyMap<String, IValue> sym3 = new MyMap<String, IValue>();
        MyMap<StringValue, BufferedReader> fileTable3 = new MyMap<>();
        MyHeap heap3 = new MyHeap();
        PrgState prg3 = new PrgState(ex3, st3, sym3,new MyList<IValue>(),fileTable3,heap3);

        IRepository repo3 = new Repository(prg3,path);
        Controller ctr3 = new Controller(repo3);

        CloseRFileStmt st49 = new CloseRFileStmt(new VarExp("varf"));
        CompStmt st48 = new CompStmt(new PrintStmt(new VarExp("varc")), st49);
        CompStmt st47 = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), st48);
        CompStmt st46 = new CompStmt(new PrintStmt(new VarExp("varc")), st47);
        CompStmt st45 = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), st46);
        CompStmt st44 = new CompStmt(new VarDeclStmt("varc", new IntType()), st45);
        CompStmt st43 = new CompStmt(new OpenRFileStmt(new VarExp("varf")), st44);
        CompStmt st42 = new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), st43);
        IStmt st4 = new CompStmt(new VarDeclStmt("varf", new StringType()), st42);
        MyHeap heap4 = new MyHeap();

        PrgState prg4 = new PrgState(st4, new MyStack<IStmt>(), new MyMap<>(), new MyList<IValue>(), new MyMap<StringValue, BufferedReader>(),heap4);

        IRepository repo4 = new Repository(prg4,path);
        Controller ctr4 = new Controller(repo4);*/

        // Example 1: test new heap memory
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a);
        VarDeclStmt varDeclStmt1 = new VarDeclStmt("v", new RefType(new IntType()));
        HeapAllocStmt allocStmt1 = new HeapAllocStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt2 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        HeapAllocStmt allocStmt2 = new HeapAllocStmt("a", new VarExp("v"));
        PrintStmt printStmt1 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt2 = new PrintStmt(new VarExp("a"));

        IStmt ex1 = new CompStmt(varDeclStmt1,
                new CompStmt(allocStmt1,
                        new CompStmt(varDeclStmt2,
                                new CompStmt(allocStmt2,
                                        new CompStmt(printStmt1, printStmt2)))));
        PrgState prgState1 = new PrgState(ex1,new MyStack<IStmt>(), new MyMap<String, IValue>(), new MyList<IValue>(),  new MyMap<StringValue, BufferedReader>(), new MyHeap());
        IRepository repository1 = new Repository(prgState1, logFilePath);
        Controller ctr1 = new Controller(repository1);

        // Example 2: test read heap
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a)) + 5);
        VarDeclStmt varDeclStmt3 = new VarDeclStmt("v", new RefType(new IntType()));
        HeapAllocStmt allocStmt3 = new HeapAllocStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt4 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        HeapAllocStmt allocStmt4 = new HeapAllocStmt("a", new VarExp("v"));
        PrintStmt printStmt3 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        PrintStmt printStmt4 = new PrintStmt(
                new ArithExp('+',new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5))));

        IStmt ex2 = new CompStmt(varDeclStmt3,
                new CompStmt(allocStmt3,
                        new CompStmt(varDeclStmt4,
                                new CompStmt(allocStmt4,
                                        new CompStmt(printStmt3, printStmt4)))));

        PrgState prgState2 = new PrgState(ex2,new MyStack<IStmt>(), new MyMap<String, IValue>(), new MyList<IValue>(),  new MyMap<StringValue, BufferedReader>(), new MyHeap());
        IRepository repository2 = new Repository(prgState2, logFilePath);
        Controller ctr2 = new Controller(repository2);

        // Example 3: test write heap
        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v) + 5);

        VarDeclStmt varDeclStmt5 = new VarDeclStmt("v", new RefType(new IntType()));
        HeapAllocStmt allocStmt5 = new HeapAllocStmt("v", new ValueExp(new IntValue(20)));
        PrintStmt printStmt5 = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        WriteHeapStmt writeHeap1 = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        PrintStmt printStmt6 = new PrintStmt(
                new ArithExp('+',new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))));
        IStmt ex3 = new CompStmt(varDeclStmt5,
                new CompStmt(allocStmt5,
                        new CompStmt(printStmt5,
                                new CompStmt(writeHeap1, printStmt6))));
        PrgState prgState3 = new PrgState(ex3,new MyStack<IStmt>(), new MyMap<String, IValue>(), new MyList<IValue>(),  new MyMap<StringValue, BufferedReader>(), new MyHeap());
        IRepository repository3 = new Repository(prgState3, logFilePath);
        Controller ctr3 = new Controller(repository3);

        // Example 4: test garbage collector
        // Ref int v;new(v,30);new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));
        VarDeclStmt varDeclStmt6 = new VarDeclStmt("v", new RefType(new IntType()));
        HeapAllocStmt allocStmt6 = new HeapAllocStmt("v", new ValueExp(new IntValue(20)));
        HeapAllocStmt allocStmt9 = new HeapAllocStmt("v", new ValueExp(new IntValue(30)));
        VarDeclStmt varDeclStmt7 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        HeapAllocStmt allocStmt7 = new HeapAllocStmt("a", new VarExp("v"));
        HeapAllocStmt allocStmt8 = new HeapAllocStmt("v", new ValueExp(new IntValue(30)));
        PrintStmt printStmt7 = new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));
        IStmt ex4 = new CompStmt(varDeclStmt6,
                new CompStmt(allocStmt9,
                    new CompStmt(allocStmt6,
                            new CompStmt(varDeclStmt7,
                                    new CompStmt(allocStmt7,
                                            new CompStmt(allocStmt8, printStmt7))))));
        PrgState prgState4 = new PrgState( ex4,new MyStack<IStmt>(), new MyMap<String, IValue>(), new MyList<IValue>(), new MyMap<StringValue, BufferedReader>(), new MyHeap());
        IRepository repository4 = new Repository(prgState4, logFilePath);
        Controller ctr4 = new Controller(repository4);

        // Example 5: test while statement
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)

        VarDeclStmt varDeclStmt8 = new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt1 = new AssignStmt("v", new ValueExp(new IntValue(4)));
        PrintStmt printStmt8 = new PrintStmt(new VarExp("v"));
        PrintStmt printStmt9 = new PrintStmt(new VarExp("v"));
        RelationalExp relationalExp1 = new RelationalExp(new VarExp("v"), ">", new ValueExp(new IntValue(0)));
        ArithExp arithExp1 = new ArithExp('-',new VarExp("v"), new ValueExp(new IntValue(1)));
        AssignStmt assignStmt2 = new AssignStmt("v", arithExp1);
        WhileStmt whileStmt1 = new WhileStmt(relationalExp1, new CompStmt(printStmt8, assignStmt2));
        IStmt ex5 = new CompStmt(varDeclStmt8,
                new CompStmt(assignStmt1,
                        new CompStmt(whileStmt1, printStmt9)));
        PrgState prgState5 = new PrgState(ex5,new MyStack<IStmt>(), new MyMap<String, IValue>(), new MyList<IValue>(),  new MyMap<StringValue, BufferedReader>(), new MyHeap());
        IRepository repository5 = new Repository(prgState5, logFilePath);
        Controller ctr5 = new Controller(repository5);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctr5));
        menu.show();
    }
}