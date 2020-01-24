package sample;

import Controller.Controller;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.File.CloseRFileStatement;
import Model.Statement.File.OpenRFileStatement;
import Model.Statement.File.ReadFileStatement;
import Model.Statement.Heap.NewStatement;
import Model.Statement.Heap.WriteHeapStatement;
import Model.Statement.Threads.LockStatement;
import Model.Statement.Threads.NewLockStatement;
import Model.Statement.Threads.UnlockStatement;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.InMemoryRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class  selectProgramFormController  implements Initializable {
    private List<IStatement> programStatements;
    private runProgramFormController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    public void setMainWindowController(runProgramFormController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    private void buildProgramStatements()
    {

        IStatement ex0 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement
                (new VarDeclarationStatement("v", new IntType()), new VarDeclarationStatement("a", new ReferenceType(new IntType()))),
                new AssignStatement("v", new ValueExpression(new IntValue(10)))), new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(30)))),
                new ForkStatement(new CompStatement(new CompStatement(new CompStatement(new WriteHeapStatement(new StringValue("a"), new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32)))),
                        new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))),
                new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))));

        // first example
        IStatement ex1 = new CompStatement(new VarDeclarationStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))));

        // second example
        IStatement ex2 = new CompStatement( new VarDeclarationStatement("a",new IntType()),
                new CompStatement(new VarDeclarationStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)), new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), "*"), "+")),
                                new CompStatement(new AssignStatement("b",new ArithmeticExpression(new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)), "+")), new PrintStatement(new VarExpression("b"))))));


        // third example
        IStatement ex3 = new CompStatement(new VarDeclarationStatement("a",new BoolType()),
                new CompStatement(new VarDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("v"))))));

        // forth example
        IStatement ex4 = new CompStatement(new CompStatement(new VarDeclarationStatement("a",new IntType()),
                new OpenRFileStatement(new ValueExpression(new StringValue("a.txt")))),
                new CompStatement(new ReadFileStatement(new ValueExpression(new StringValue("a.txt")), new StringValue("a")),
                        new CompStatement(new PrintStatement(new VarExpression("a")),
                                new CompStatement(new IfStatement(new VarExpression("a"),
                                        new CompStatement(new ReadFileStatement(new ValueExpression(new StringValue("a.txt")), new StringValue("a")),
                                                new PrintStatement(new VarExpression("a"))), new PrintStatement(new ValueExpression(new IntValue(0)))),
                                        new CloseRFileStatement(new ValueExpression(new StringValue("a.txt")))))));


        // fifth example
        IStatement ex5 = new CompStatement(new CompStatement(new VarDeclarationStatement("a",new IntType()), new VarDeclarationStatement("b",new IntType())),
                new CompStatement(new OpenRFileStatement(new ValueExpression(new StringValue("a.txt"))), new CompStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("a.txt")), new StringValue("a")), new CompStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("a.txt")), new StringValue("b")),   new CompStatement(new PrintStatement(new VarExpression("a")),
                        new CompStatement(new PrintStatement(new VarExpression("b")),new CloseRFileStatement(new ValueExpression(new StringValue("a.txt")))))))));


        // sixth example
        IStatement ex6 = new CompStatement(new CompStatement(new VarDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new CompStatement(new CompStatement(new VarDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new NewStatement(new StringValue("a"), new VarExpression("v"))),  new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))));

        //seventh example
        IStatement ex7 = new CompStatement( new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement
                ("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new VarDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"),
                new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("v")))), new PrintStatement(
                new ArithmeticExpression(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))),
                        new ValueExpression(new IntValue(5)), "+")));

        //eighth example
        IStatement ex8 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("v", new ReferenceType(new IntType()))
                , new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))), new PrintStatement(new HeapReadingExpression(new VarExpression("v")))),
                new WriteHeapStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new ArithmeticExpression(new HeapReadingExpression(new VarExpression("v")),
                new ValueExpression(new IntValue(5)), "+")));

        //ninth example
        IStatement ex9 = new CompStatement(new VarDeclarationStatement("v",new IntType()), new CompStatement(
                new AssignStatement("v",new ValueExpression(new IntValue(10))), new WhileStatement(
                new RelationalExpression(new VarExpression("v"),new ValueExpression(new IntValue(0)), ">"),
                new CompStatement(new PrintStatement(new VarExpression("v")),
                        new AssignStatement( "v",new ArithmeticExpression(new VarExpression("v"),
                                new ValueExpression(new IntValue(1)), "-"))))));

        //tenth example
        IStatement ex10 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("v",
                new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))), new VarDeclarationStatement("a",
                new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"), new VarExpression("v"))),
                new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new HeapReadingExpression(
                new HeapReadingExpression(new VarExpression("a")))));

        //eleventh example
        IStatement ex11 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement
                (new VarDeclarationStatement("v", new IntType()), new VarDeclarationStatement("a", new ReferenceType(new IntType()))),
                new AssignStatement("v", new ValueExpression(new IntValue(10)))), new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(22)))),
                new ForkStatement(new CompStatement(new CompStatement(new CompStatement(new WriteHeapStatement(new StringValue("a"), new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32)))),
                        new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))),
                new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))));

        //12

        IStatement ex12 = new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("a", new ReferenceType(new IntType())),
                new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(20)))),
                new ForStatement( new ValueExpression(new IntValue(-1)),
                        new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), "+"),
                        new ForkStatement(new CompStatement(new PrintStatement(new VarExpression("v")),
                                new AssignStatement("v", new HeapReadingExpression(new ArithmeticExpression(
                                        new VarExpression("v"), new VarExpression("a"), "*"))))))),
                            new PrintStatement(new HeapReadingExpression(new VarExpression("a"))));

        /*
        IStatement fork2 = new ForkStatement(new CompStatement(new ForkStatement(new CompStatement(new CompStatement(new LockStatement("q"), new WriteHeapStatement(new StringValue("v1"),
                new ArithmeticExpression( new HeapReadingExpression(new VarExpression("v2")), new ValueExpression(new IntValue(5)), "+"))),
                new UnlockStatement("q"))), new CompStatement(new CompStatement(new LockStatement("q"),
                new WriteHeapStatement(new StringValue("v2"), new HeapReadingExpression(new ArithmeticExpression( new VarExpression("v2"), new ValueExpression(new IntValue(10)), "*")))),
                new UnlockStatement("q"))));

        IStatement lock1 = new CompStatement(new CompStatement(new LockStatement("x"), new PrintStatement(new HeapReadingExpression(new VarExpression("v1")))), new UnlockStatement("x"));
        IStatement lock2 = new CompStatement(new CompStatement(new LockStatement("q"), new PrintStatement(new HeapReadingExpression(new VarExpression("v2")))), new UnlockStatement("q"));

         */

        IStatement ex14 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(
                new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("v1", new ReferenceType(new IntType())), new VarDeclarationStatement("v2", new ReferenceType(new IntType()))),
                new VarDeclarationStatement("x", new IntType())), new VarDeclarationStatement("q", new IntType())), new NewStatement(new StringValue("v1"), new ValueExpression(new IntValue(190)))),
                new NewStatement(new StringValue("v2"), new ValueExpression(new IntValue(305)))), new NewLockStatement("x")),
                new ForkStatement(new CompStatement(new ForkStatement(new CompStatement(new CompStatement(new LockStatement("x"), new WriteHeapStatement(new StringValue("v1"),
                        new ArithmeticExpression( new HeapReadingExpression(new VarExpression("v1")), new VarExpression("1"), "-"))),
                        new UnlockStatement("x"))), new CompStatement(new CompStatement(new LockStatement("x"),
                        new WriteHeapStatement(new StringValue("v2"), new HeapReadingExpression(new ArithmeticExpression( new VarExpression("v1"), new ValueExpression(new IntValue(10)), "*")))),
                        new UnlockStatement("x"))))), new NewLockStatement("q")), new ForkStatement(new CompStatement(new ForkStatement(new CompStatement(new CompStatement(new LockStatement("q"), new WriteHeapStatement(new StringValue("v1"),
                new ArithmeticExpression( new HeapReadingExpression(new VarExpression("v2")), new ValueExpression(new IntValue(5)), "+"))),
                new UnlockStatement("q"))), new CompStatement(new CompStatement(new LockStatement("q"),
                new WriteHeapStatement(new StringValue("v2"), new HeapReadingExpression(new ArithmeticExpression( new VarExpression("v2"), new ValueExpression(new IntValue(10)), "*")))),
                new UnlockStatement("q"))))),
                new CompStatement(new CompStatement(new LockStatement("x"), new PrintStatement(new HeapReadingExpression(new VarExpression("v1")))),
                        new UnlockStatement("x"))), new CompStatement(new CompStatement(new LockStatement("q"), new PrintStatement(
                                new HeapReadingExpression(new VarExpression("v2")))), new UnlockStatement("q")));


        programStatements=new ArrayList<>(Arrays.asList(ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10,ex11, ex12,ex14));
    }


    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();

            if(index < 0)
                return;

            ProgramState initialProgramState = new ProgramState(programStatements.get(index));
            //index++;
            IRepository repository = new InMemoryRepository(initialProgramState, "log" + index + ".txt");
            Controller ctrl = new Controller(repository);

            mainWindowController.setController(ctrl);
        });


    }

}
