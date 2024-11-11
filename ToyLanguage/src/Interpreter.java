import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.ArithExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
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

        String path;
        // read path
        Scanner scanner1 = new Scanner(System.in);
        path = scanner1.next();

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        MyStack<IStmt> st = new MyStack<>();
        MyDictionary<String, IValue> sym = new MyDictionary<>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        PrgState prg1 = new PrgState(ex1, st, sym,new MyList<IValue>(),fileTable);

        IRepository repo1 = new Repository(prg1,path);
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new

                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        MyStack<IStmt> st2 = new MyStack<>();
        MyDictionary<String, IValue> sym2 = new MyDictionary<>();
        MyDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        PrgState prg2 = new PrgState(ex2, st2, sym2,new MyList<IValue>(),fileTable2);


        IRepository repo2 = new Repository(prg2,path);
        Controller ctr2 = new Controller(repo2);


        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(false))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        MyStack<IStmt> st3 = new MyStack<IStmt>();
        MyDictionary<String, IValue> sym3 = new MyDictionary<String, IValue>();
        MyDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        PrgState prg3 = new PrgState(ex3, st3, sym3,new MyList<IValue>(),fileTable3);

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
        PrgState prg4 = new PrgState(st4, new MyStack<IStmt>(), new MyDictionary<>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>());

        IRepository repo4 = new Repository(prg4,path);
        Controller ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExample("4",st4.toString(),ctr4));
        menu.show();
    }
}