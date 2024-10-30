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


public class View {
    private Controller controller;

   // public PrgState(IStmt initState, MyStack<IStmt> execStack, MyDictionary<String, IValue> symTable)

    public View() {
        Repository repo = new Repository();
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExpression(new IntValue(2))), new PrintStmt(new
                        VariableExpression("v"))));
        MyStack<IStmt> st = new MyStack<IStmt>();
        MyDictionary<String, IValue> sym = new MyDictionary<String, IValue>();
        PrgState prg = new PrgState(ex1, st, sym);
        repo.addPrgState(prg);
        this.controller = new Controller(repo);
    }

    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printMenu(){
        System.out.println("1. Run an existing program// Input program");
        System.out.println("2. Execute a program");
        System.out.println("0. Exit");
    }


}
