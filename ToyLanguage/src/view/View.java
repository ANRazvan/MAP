package view;
import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyStack;
import repository.Repository;
import model.state.*;
import model.statements.*;
import model.expressions.*;
import model.type.*;
import model.value.*;

import java.util.Scanner;

import static java.lang.System.exit;


public class View {
    private Controller controller;

   // public PrgState(IStmt initState, MyStack<IStmt> execStack, MyDictionary<String, IValue> symTable)

    public View() {
        Repository repo = new Repository();
        this.controller = new Controller(repo);
    }

    public void selectProgram() {
        System.out.println("Select program (1/2/3): ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch(option){
            case 1:
                IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                        new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                                VarExp("v"))));
                MyStack<IStmt> st = new MyStack<>();
                MyDictionary<String, IValue> sym = new MyDictionary<>();
                PrgState prg = new PrgState(ex1, st, sym);
                controller.getRepo().addPrgState(prg);
                System.out.println("Program 1 added to the repository.");
                break;
            case 2:
                IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                        new CompStmt(new VarDeclStmt("b",new IntType()),
                                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                        ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                        new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                                IntValue(1)))), new PrintStmt(new VarExp("b"))))));
                MyStack<IStmt> st2 = new MyStack<>();
                MyDictionary<String, IValue> sym2 = new MyDictionary<>();
                PrgState prg2 = new PrgState(ex2, st2, sym2);
                controller.getRepo().addPrgState(prg2);
                System.out.println("Program 2 added to the repository.");
                break;
            case 3:
                IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                                IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                                VarExp("v"))))));
                MyStack<IStmt> st3 = new MyStack<IStmt>();
                MyDictionary<String, IValue> sym3 = new MyDictionary<String, IValue>();
                PrgState prg3 = new PrgState(ex3, st3, sym3);
                controller.getRepo().addPrgState(prg3);
                break;

        }
    }

    public void run() {

            for(;;){
                try {
                    printMenu();
                    Scanner scanner = new Scanner(System.in);
                    int option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("Input/Select program");
                            selectProgram();
                            break;
                        case 2:
                            System.out.println("Execute program");
                            controller.allStep();
                            break;
                        case 0:
                            System.out.println("Exit");
                            exit(0);
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                }   catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
            }



    }

    public void printMenu(){
        System.out.println("1. Input/Select program");
        System.out.println("2. Execute program");
        System.out.println("0. Exit");
    }


}
