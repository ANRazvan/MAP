package gui;
import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.adt.*;
import model.expressions.ArithExp;
import model.expressions.VarExp;
import model.state.PrgState;
import model.statements.*;
import model.type.IntType;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GUIController {
    /**
     * Initializes the controller class. This method is
     automatically called
     * after the fxml file has been loaded.
     */

    @FXML
    private TableView<Pair<Integer, String>> heapTableView;
    @FXML
    private TableColumn<Pair<Integer, String>, String> heapAddressCol;
    @FXML
    private TableColumn<Pair<Integer, String>, String> heapValueCol;

    @FXML
    private TableView<Pair<Integer, Integer>> LockTableView;
    @FXML
    private TableColumn<Pair<Integer, String>, String> LocationCol;
    @FXML
    private TableColumn<Pair<Integer, String>, String> ValueCol;

    @FXML
    private TableView<Pair<String, String>> symTableView;
    @FXML
    private TableColumn<Pair<String, String>, String> symVarNameCol;
    @FXML
    private TableColumn<Pair<String, String>, String> symValueCol;

    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> prgStateListView;

    @FXML
    private TableView<Pair<String, String>> ProcedureTableView;

    @FXML
    private TableColumn<Pair<String, String>, String> SignatureCol;

    @FXML
    private TableColumn<Pair<String, String>, String> BodyCol;

    @FXML
    private Button runButton;

    @FXML
    private TextField numPrgStatesTextField;

    private Controller controller;
    private Repository repo;
    private MyIProcedureTable procedureTable = new MyProcedureTable();

    public void initializeExecution(IStmt program) {
        IStmt f1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp('+', new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        IStmt f2 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp('*', new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );

//        procedureTable.insert("sum", new Pair<>(Arrays.asList("a", "b"), f1));
//        procedureTable.insert("product", new Pair<>(Arrays.asList("a", "b"), f2));
        PrgState prgState = new PrgState(program, new MyStack<>(), new MyMap<>(), new MyList<>(), new MyMap<>(), new MyHeap(), new MyLockTable(), procedureTable);
        repo = new Repository(prgState, "log.txt");
        controller = new Controller(repo);

        prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PrgState selectedPrgState = getCurrentPrgState();
                if (selectedPrgState != null) {
                    updateSymTable(selectedPrgState);
                    updateExeStack(selectedPrgState);
                }
            }
        });
        updateProcedureTable();
        updateGUI();
    }


    @FXML
    private void handleRunOneStep() {
        try {
            controller.executor = Executors.newFixedThreadPool(2);
            List<PrgState> prgList = controller.removeCompletedPrg(repo.getPrgList());
            if(prgList.isEmpty()){
                showError("Program is finished!");
                return;

            }
            // Call conservative garbage collector
            prgList.forEach(prg -> prg.getHeap().setContent(
                    controller.conservativeGarbageCollector(prgList)
            ));
            controller.oneStepForAllPrg(prgList);
            controller.executor.shutdownNow();
            updateGUI();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("One step executed");

    }

    private void updateGUI() {
        List<PrgState> prgStates = controller.getRepo().getPrgList();
        numPrgStatesTextField.setText(String.valueOf(prgStates.size()));
        if(prgStates.isEmpty()){
            exeStackListView.getItems().clear();
            prgStateListView.getItems().clear();
            return;
        }
        ObservableList<Integer> prgStateIds = FXCollections.observableArrayList(
                prgStates.stream().map(PrgState::getId).collect(Collectors.toList())
        );
        prgStateListView.setItems(prgStateIds);
        Integer currentSelection = prgStateListView.getSelectionModel().getSelectedItem();

        if (currentSelection == null && !prgStateIds.isEmpty()) {
            prgStateListView.getSelectionModel().select(0);
        }
        PrgState selectedPrgState = getCurrentPrgState();
        updateLockTable(selectedPrgState);
        updateHeapTable(selectedPrgState);
        updateSymTable(selectedPrgState); // Assume one PrgState for simplicity
        updateExeStack(selectedPrgState);
        updateOutputList(prgStates);
        updateFileTable(prgStates);
    }
    private PrgState getCurrentPrgState() {
        Integer currentSelection = prgStateListView.getSelectionModel().getSelectedItem();
        if (currentSelection == null || prgStateListView.getSelectionModel().isEmpty()) {
            return null;
        }
        List<PrgState> prgStates = repo.getPrgList();
        return prgStates.stream().filter(prgState -> prgState.getId() == currentSelection).findFirst().orElse(null);
    }

    private void updateProcedureTable() {
        ObservableList<Pair<String, String>> procedureTableItems = FXCollections.observableArrayList(
                procedureTable.getContent().entrySet().stream()
                        .map(entry -> {
                            String functionName = entry.getKey();
                            List<String> parameters = entry.getValue().getKey();
                            IStmt functionBody = entry.getValue().getValue();

                            // Format: functionName(param1, param2, ...)
                            String signature = functionName + "(" + String.join(", ", parameters) + ")";

                            // Convert function body to string representation
                            String body = functionBody.toString();

                            return new Pair<>(signature, body);
                        })
                        .collect(Collectors.toList())
        );

        // Bind Procedure Table columns
        SignatureCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        BodyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        ProcedureTableView.setItems(procedureTableItems);
    }


    private void updateLockTable(PrgState prgState) {
        ObservableList<Pair<Integer, Integer>> lockTableItems = FXCollections.observableArrayList(
                prgState.getLockTable().getContent().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        );
        // Bind Lock Table columns
        LocationCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getKey())));
        ValueCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue())));
        LockTableView.setItems(lockTableItems);
    }

    private void updateHeapTable(PrgState prgState) {
        ObservableList<Pair<Integer, String>> heapTableItems = FXCollections.observableArrayList(
                prgState.getHeap().getContent().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList())
        );
        // Bind Heap Table columns
        heapAddressCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getKey())));
        heapValueCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));
        heapTableView.setItems(heapTableItems);
    }

    private void updateSymTable(PrgState prgState) {
        ObservableList<Pair<String, String>> symTableItems = FXCollections.observableArrayList(
                prgState.getSymTable().getContent().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList())
        );
        // Bind Sym Table columns
        symVarNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        symValueCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().toString()));

        symTableView.setItems(symTableItems);

    }
    private void updateExeStack(PrgState prgState) {
        MyIStack<IStmt> exeStack = prgState.getExecStack();
        ObservableList<String> stackItems = FXCollections.observableArrayList(
                exeStack.getContentAsList().stream().map(IStmt::toString).collect(Collectors.toList())
        );
        exeStackListView.setItems(stackItems);
    }

    private void updateOutputList(List<PrgState> prgStates) {
        MyIList<IValue> output = prgStates.getFirst().getOutputList();
        // dont use output.getContent()
        ObservableList<String> outputItems = FXCollections.observableArrayList(
                output.getAll().stream().map(IValue::toString).collect(Collectors.toList())
        );
        outputListView.setItems(outputItems);
    }
    private void updateFileTable(List<PrgState> prgStates) {
        MyIMap<StringValue, BufferedReader> fileTable = prgStates.getFirst().getFileTable();
        ObservableList<String> fileEntries = FXCollections.observableArrayList(
                fileTable.getContent().keySet().stream().map(StringValue::toString).collect(Collectors.toList())
        );
        fileTableListView.setItems(fileEntries);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}