package gui;
import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.adt.*;
import model.state.PrgState;
import model.statements.*;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import java.io.BufferedReader;
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
    private Button runButton;

    @FXML
    private TextField numPrgStatesTextField;

    private Controller controller;
    private Repository repo;

    public void initializeExecution(IStmt program) {
       PrgState prgState = new PrgState(program, new MyStack<>(), new MyMap<>(), new MyList<>(), new MyMap<>(), new MyHeap());
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
            Map<Integer, IValue> heap = controller.conservativeGarbageCollector(prgList);
            controller.oneStepForAllPrg(prgList);
            prgList = controller.removeCompletedPrg(repo.getPrgList());
            controller.executor.shutdownNow();
            repo.setPrgList(prgList);
            updateGUI();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("One step executed");

    }

    private void updateGUI() {
        List<PrgState> prgStates = repo.getPrgList();
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
