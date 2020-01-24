package sample;


import Controller.Controller;
import Model.Collection.*;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Values.IValue;
import Model.Values.StringValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class runProgramFormController implements Initializable {

    private Controller controller;
    private int currentId=0;

    @FXML
    private TableView<Map.Entry<Integer,IValue>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, IValue> heapValueColumn;

    @FXML
    private TableView<Map.Entry<IValue, BufferedReader>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<IValue, BufferedReader>, IValue> fileIdentifierColumn;

    @FXML
    private TableColumn<Map.Entry<IValue, BufferedReader>, IValue> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, IValue>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, IValue> symbolTableValueColumn;

    @FXML
    private ListView<IValue> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    @FXML
    private TableView<Map.Entry<String, IValue>> lockTableView;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> lockTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, IValue> lockTableValueColumn;

    public void setController(Controller controller)
    {
        this.controller=controller;
        populateProgramStateIdentifiers();
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList)
    {
        return programStateList.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers()
    {
        List<ProgramState> programStates=controller.getRepository().getProgramStateList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

        numberOfProgramStatesTextField.setText(""+programStates.size() );
    }

    private void executeOneStep()
    {
        if(controller==null)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Program not seleced",ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean programStateLeft = controller.getRepository().getProgramStateWithId(currentId).getExeStack().isEmpty();
        if(programStateLeft){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        controller.executeOneStep();

        changeProgramState(controller.getRepository().getProgramStateWithId(currentId));
        populateProgramStateIdentifiers();

    }

    public void changeProgramState(ProgramState currentProgramState)
    {
        if(currentProgramState==null)
            return;
        populateExecutionStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutput(currentProgramState);
        populateLockTable(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeapTable(currentProgramState);
    }

    private void populateHeapTable(ProgramState currentProgramState)
    {
        IHeap<Integer,IValue> heapTable=currentProgramState.getHeapTable();

        List<Map.Entry<Integer,IValue>> heapTableList=new ArrayList<>();
        for(Map.Entry<Integer,IValue> entry : heapTable.getEntrySet())
        {
            heapTableList.add(entry);
        }
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState)
    {
        MyIDictionary<StringValue,BufferedReader> fileTable=currentProgramState.getFileTable();
        Map<IValue,BufferedReader> fileTableMap=new HashMap<>();
        for(Map.Entry<StringValue,BufferedReader> entry: fileTable.getAll())
        {
            fileTableMap.put(entry.getKey(),entry.getValue());

        }
        List<Map.Entry<IValue,BufferedReader>> fileTableList=new ArrayList<>(fileTableMap.entrySet());
        fileTableView.setItems(FXCollections.observableList(fileTableList));
        fileTableView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        MyIList<IValue> ot = currentProgramState.getOutput();
        ArrayList<IValue> output = new ArrayList<>();
        for (int i = 0; i < ot.size(); i++){
            output.add(ot.get(i));
        }
        //List<IValue> output =(List<IValue>) currentProgramState.getOutput();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    public void populateSymbolTable(ProgramState currentProgramState)
    {
        MyIDictionary<String, IValue> symbolTable=currentProgramState.getSymTable();

        List<Map.Entry<String,IValue>> symbolTableList=new ArrayList<>();
        for(Map.Entry<String, IValue> entry : symbolTable.getAll())
        {

            symbolTableList.add((entry));
        }
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();


    }

    public void populateLockTable(ProgramState currentProgramState)
    {
        ILockTable procTbl=currentProgramState.getLockTable();

        List<Map.Entry<String,IValue>> lockTableList=new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry : procTbl.getAll())
        {

            lockTableList.add(Map.entry(entry.getKey().toString(),new StringValue(entry.getValue().toString())));
        }
        lockTableView.setItems(FXCollections.observableList(lockTableList));
        lockTableView.refresh();


    }

    public void populateExecutionStack(ProgramState currentProgramState)
    {
        MyIStack<IStatement> executionStack=currentProgramState.getExeStack();

        List<String> executionStackList=new ArrayList<>();
        for(IStatement s:executionStack.getAll())
        {
            executionStackList.add(s.toString());
        }
        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }

    private ProgramState getCurrentProgramState()
    {
        if(programStateListView.getSelectionModel().getSelectedIndex()==-1)
            return null;

        currentId=programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStateWithId(currentId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        heapAddressColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        fileIdentifierColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        fileNameColumn.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue().toString() + ""));
        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        lockTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        lockTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        programStateListView.setOnMouseClicked(mouseEvent -> { changeProgramState(getCurrentProgramState()); });

        executeOneStepButton.setOnAction(actionEvent -> { executeOneStep(); });
    }



}
