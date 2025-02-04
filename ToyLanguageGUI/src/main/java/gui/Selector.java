package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.state.PrgState;
import model.statements.IStmt;
import model.statements.VarDeclStmt;
import model.type.IType;
import model.type.IntType;
import model.type.RefType;
import model.value.IntValue;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

import model.adt.*;
import model.state.PrgState;
import model.statements.*;
import model.expressions.*;
import repository.*;
import controller.*;
import view.*;

import javax.management.relation.Relation;
import java.util.List;

public class Selector {
    @FXML
    private ListView<String> programListView;
    @FXML
    private Button selectProgramButton;

    private List<IStmt> programs; // List of IStmt instances

    public void initialize() {
        programs = loadPrograms();
        programListView.setItems(FXCollections.observableArrayList(
                programs.stream().map(IStmt::toString).toList()
        ));
    }

    // Write the program examples
    private List<IStmt> loadPrograms() {
        List<IStmt> programExamples = new ArrayList<>();
        IStmt s1 = new VarDeclStmt("v", new IntType());
        IStmt s2 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt s3 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        IStmt s4 = new HeapAllocStmt("a", new ValueExp(new IntValue(22)));
        IStmt s6 = new WriteHeapStmt("a", new ValueExp(new IntValue(30)));
        IStmt s7 = new AssignStmt("v", new ValueExp(new IntValue(32)));
        IStmt s8 = new PrintStmt(new VarExp("v"));
        IStmt s9 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        IStmt s5 = new CompStmt(s9, new CompStmt(s7, new CompStmt(s8, s6)));
        IStmt s10 = new PrintStmt(new VarExp("v"));
        IStmt s11 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        IStmt ex1 = new CompStmt(s1, new CompStmt(s2, new CompStmt(s3, new CompStmt(s4, new CompStmt(new ForkStmt(new CompStmt(new VarDeclStmt("c",new IntType()),s5)), new CompStmt(s10, s11))))));
        MyIMap<String, IType> typeEnv = new MyMap<String,IType>();
//        try {
//            ex1.typecheck(typeEnv);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
        System.out.println("Finished typechecking");
        programExamples.add(ex1);

        // an example for a for stmt
        /*
        Ref int a; new(a,20);
        (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        print(rh(a))
        The final Out should be {0,1,2,20}

         Statement ex12 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                        new CompStmt(new ForStatement("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new AssignmentStatement("v", new ArithExp('*', new VarExp("v"), new ReadHeapExp(new VarExp("a"))))))),
                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))))));

         */
        IStmt f1 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt f2 = new HeapAllocStmt("a", new ValueExp(new IntValue(20)));
        IStmt fstmt = new ForStmt("v",new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))), new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp('*',new VarExp("v"),new ReadHeapExp(new VarExp("a")))))));
        IStmt f3 = new PrintStmt(new ReadHeapExp(new VarExp("a")));
        IStmt forstmt = new CompStmt(f1, new CompStmt(f2, new CompStmt(fstmt, f3)));
        programExamples.add(forstmt);
        try {
            forstmt.typecheck(typeEnv);
            System.out.println("Finished typechecking ex2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        /*
        Ref int v1; Ref int v2; int x; int q;
new(v1,20);new(v2,30);newLock(x);
fork(
 fork(
 lock(x);wh(v1,rh(v1)-1);unlock(x)
 );
 lock(x);wh(v1,rh(v1)*10);unlock(x)
);newLock(q);
fork(
 fork(lock(q);wh(v2,rh(v2)+5);unlock(q));
 lock(q);wh(v2,rh(v2)*10);unlock(q)
);
nop;nop;nop;nop;
lock(x); print(rh(v1)); unlock(x);
lock(q); print(rh(v2)); unlock(q);
The final Out should be {190 or 199,350 or 305}
         

        IStmt l1 = new VarDeclStmt("v1", new RefType(new IntType()));
        IStmt l2 = new VarDeclStmt("v2", new RefType(new IntType()));
        IStmt l3 = new VarDeclStmt("x", new IntType());
        IStmt l4 = new VarDeclStmt("q", new IntType());
        IStmt l5 = new HeapAllocStmt("v1", new ValueExp(new IntValue(20)));
        IStmt l6 = new HeapAllocStmt("v2", new ValueExp(new IntValue(30)));
        IStmt l7 = new NewLockStatement("x");
        IStmt fork2 = new ForkStmt(new ForkStmt(
                                                new CompStmt(new LockStatement("x"),new CompStmt)
        )
        )
*/
        IStmt ex13 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new VarDeclStmt("q", new IntType()),
                                        new CompStmt(new HeapAllocStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(new HeapAllocStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new NewLockStatement("x"),
                                                                new CompStmt(new ForkStmt(
                                                                        new CompStmt(new ForkStmt(
                                                                                new CompStmt(new LockStatement("x"),
                                                                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                                                                new UnlockStatement("x")))
                                                                        ),
                                                                                new CompStmt(new LockStatement("x"),
                                                                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                new UnlockStatement("x"))))
                                                                ),
                                                                        new CompStmt( new NewLockStatement("q"),
                                                                                new CompStmt(new ForkStmt(
                                                                                        new CompStmt( new ForkStmt(
                                                                                                new CompStmt(new LockStatement("q"),
                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)))),
                                                                                                                new UnlockStatement("q")))
                                                                                        ),
                                                                                                new CompStmt(new LockStatement("q"),
                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                new UnlockStatement("q"))))
                                                                                ),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new LockStatement("x"),
                                                                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                        new CompStmt(new UnlockStatement("x"),
                                                                                                                                                new CompStmt(new LockStatement("q"),
                                                                                                                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                new UnlockStatement("q"))))))))))))))))))));
        programExamples.add(ex13);
        try {
            ex13.typecheck(typeEnv);
            System.out.println("Finished typechecking ex3");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        IStmt switch1 = new VarDeclStmt("a", new IntType());
        IStmt switch2 = new VarDeclStmt("b", new IntType());
        IStmt switch3 = new VarDeclStmt("c", new IntType());
        IStmt switch5 = new AssignStmt("a", new ValueExp(new IntValue(1)));
        IStmt switch6 = new AssignStmt("b", new ValueExp(new IntValue(2)));
        IStmt switch7 = new AssignStmt("c", new ValueExp(new IntValue(5)));
        IExpression exp = new ArithExp('*',new VarExp("a"),new ValueExp(new IntValue(10)));
        IExpression exp1 = new ArithExp('*',new VarExp("b"),new VarExp("c"));
        IStmt stmt1 = new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b")));
        IExpression exp2 = new ValueExp(new IntValue(10));
        IStmt stmt2 = new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200))));
        IStmt stmt3 = new PrintStmt(new ValueExp(new IntValue(300)));
        IStmt switchstmt = new SwitchStatement(exp,exp1,stmt1,exp2,stmt2,stmt3);
        IStmt mainswitch = new CompStmt(switch1,new CompStmt(switch2,new CompStmt(switch3,new CompStmt(switch5,new CompStmt(switch6,new CompStmt(switch7,new CompStmt(switchstmt, new PrintStmt(new ValueExp(new IntValue(300))))))))));
        programExamples.add(mainswitch);


        IStmt ru1 = new VarDeclStmt("v", new IntType());
        IStmt ru2 = new AssignStmt("v", new ValueExp(new IntValue(0)));
        IStmt rustmt = new RepeatUntilStmt(
                new CompStmt(
                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1))))
                        )),new AssignStmt("v",new ArithExp('+',new VarExp("v"),new ValueExp(new IntValue(1))))),new RelationalExp(new VarExp("v"),"=",new ValueExp(new IntValue(3))));

        IStmt ru3 = new VarDeclStmt("x", new IntType());
        IStmt ru4 = new AssignStmt("x", new ValueExp(new IntValue(1)));
        IStmt ru5 = new VarDeclStmt("y", new IntType());
        IStmt ru6 = new AssignStmt("y", new ValueExp(new IntValue(2)));
        IStmt ru7 = new VarDeclStmt("z", new IntType());
        IStmt ru8 = new AssignStmt("z", new ValueExp(new IntValue(3)));
        IStmt ru9 = new VarDeclStmt("w", new IntType());
        IStmt ru10 = new AssignStmt("w", new ValueExp(new IntValue(4)));
        IStmt ru11 = new PrintStmt(new ArithExp('*',new VarExp("v"),new ValueExp(new IntValue(10))));
        IStmt mainru = new CompStmt(ru1,new CompStmt(ru2,new CompStmt(rustmt,new CompStmt(ru3,new CompStmt(ru4,new CompStmt(ru5,new CompStmt(ru6,new CompStmt(ru7,new CompStmt(ru8,new CompStmt(ru9,new CompStmt(ru10,ru11)))))))))));

        programExamples.add(mainru);


        return programExamples;
    }

    @FXML
    private void handleSelectProgram() {
        int selectedIndex = programListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            IStmt selectedProgram = programs.get(selectedIndex);
            Stage currentStage = (Stage) selectProgramButton.getScene().getWindow();
            currentStage.close();

            GUI.launch(selectedProgram);
        }
    }
}