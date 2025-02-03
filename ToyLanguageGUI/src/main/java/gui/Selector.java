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
        IStmt ex6 = new CompStmt(s1, new CompStmt(s2, new CompStmt(s3, new CompStmt(s4, new CompStmt(new ForkStmt(new CompStmt(new VarDeclStmt("c",new IntType()),s5)), new CompStmt(s10, s11))))));
        MyIMap<String, IType> typeEnv = new MyMap<String,IType>();
        try {
            ex6.typecheck(typeEnv);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("Finished typechecking");
        programExamples.add(ex6);
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