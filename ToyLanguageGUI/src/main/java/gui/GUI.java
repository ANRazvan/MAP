package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.stage.WindowEvent;
import model.adt.*;
import model.expressions.ReadHeapExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.statements.*;
import model.type.IType;
import model.type.IntType;
import model.type.RefType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class GUI  {

    public static void launch(IStmt program) {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource("/gui/View.fxml"));
            AnchorPane root = loader.load();

            GUIController controller = loader.getController();
            controller.initializeExecution(program);

            Scene scene = new Scene(root);
            stage.setTitle("Program Execution");
            stage.setScene(scene);

            // Handle cleanup
            stage.setOnCloseRequest((WindowEvent event) -> Platform.exit());

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();

    }


    }

}
