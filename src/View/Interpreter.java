//package View;

import Controller.Controller;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.File.CloseRFileStatement;
import Model.Statement.File.OpenRFileStatement;
import Model.Statement.File.ReadFileStatement;
import Model.Statement.Heap.NewStatement;
import Model.Statement.Heap.WriteHeapStatement;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.InMemoryRepository;

import java.util.ArrayList;
/*
class Interpreter {
    public static void main(String[] args) {
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

        ArrayList<ProgramState> prg1 = new ArrayList<>();
        prg1.add(new ProgramState(ex1));
        IRepository repo1 = new InMemoryRepository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        // second example
        IStatement ex2 = new CompStatement( new VarDeclarationStatement("a",new IntType()),
                new CompStatement(new VarDeclarationStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)), new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), "*"), "+")),
                                new CompStatement(new AssignStatement("b",new ArithmeticExpression(new VarExpression("a"), new
                                        ValueExpression(new IntValue(1)), "+")), new PrintStatement(new VarExpression("b"))))));


        ArrayList<ProgramState> prg2 = new ArrayList<>();
        prg2.add(new ProgramState(ex2));
        IRepository repo2 = new InMemoryRepository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        // third example
        IStatement ex3 = new CompStatement(new VarDeclarationStatement("a",new BoolType()),
                new CompStatement(new VarDeclarationStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VarExpression("v"))))));

        ArrayList<ProgramState> prg3 = new ArrayList<>();
        prg3.add(new ProgramState(ex3));
        IRepository repo3 = new InMemoryRepository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        // forth example
        IStatement ex4 = new CompStatement(new CompStatement(new VarDeclarationStatement("a",new IntType()),
                new OpenRFileStatement(new ValueExpression(new StringValue("a.txt")))),
                new CompStatement(new ReadFileStatement(new ValueExpression(new StringValue("a.txt")), new StringValue("a")),
                        new CompStatement(new PrintStatement(new VarExpression("a")),
                                new CompStatement(new IfStatement(new VarExpression("a"),
                                        new CompStatement(new ReadFileStatement(new ValueExpression(new StringValue("a.txt")), new StringValue("a")),
                                                new PrintStatement(new VarExpression("a"))), new PrintStatement(new ValueExpression(new IntValue(0)))),
                                        new CloseRFileStatement(new ValueExpression(new StringValue("a.txt")))))));

        ArrayList<ProgramState> prg4 = new ArrayList<>();
        prg4.add(new ProgramState(ex4));
        IRepository repo4 = new InMemoryRepository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        // fifth example
        IStatement ex5 = new CompStatement(new CompStatement(new VarDeclarationStatement("a",new IntType()), new VarDeclarationStatement("b",new IntType())),
                new CompStatement(new OpenRFileStatement(new ValueExpression(new StringValue("a.txt"))), new CompStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("a.txt")), new StringValue("a")), new CompStatement( new ReadFileStatement
                        (new ValueExpression(new StringValue("a.txt")), new StringValue("b")),   new CompStatement(new PrintStatement(new VarExpression("a")),
                        new CompStatement(new PrintStatement(new VarExpression("b")),new CloseRFileStatement(new ValueExpression(new StringValue("a.txt")))))))));

        ArrayList<ProgramState> prg5 = new ArrayList<>();
        prg5.add(new ProgramState(ex5));
        IRepository repo5 = new InMemoryRepository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        // sixth example
        IStatement ex6 = new CompStatement(new CompStatement(new VarDeclarationStatement("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new CompStatement(new CompStatement(new VarDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new NewStatement(new StringValue("a"), new VarExpression("v"))),  new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))));

        ArrayList<ProgramState> prg6 = new ArrayList<>();
        prg6.add(new ProgramState(ex6));
        IRepository repo6 = new InMemoryRepository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        //seventh example
        IStatement ex7 = new CompStatement( new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement
                ("v", new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))),
                new VarDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"),
                new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("v")))), new PrintStatement(
                new ArithmeticExpression(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))),
                        new ValueExpression(new IntValue(5)), "+")));

        ArrayList<ProgramState> prg7 = new ArrayList<>();
        prg7.add(new ProgramState(ex7));
        IRepository repo7 = new InMemoryRepository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        //eighth example
        IStatement ex8 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("v", new ReferenceType(new IntType()))
                , new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))), new PrintStatement(new HeapReadingExpression(new VarExpression("v")))),
                new WriteHeapStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new ArithmeticExpression(new HeapReadingExpression(new VarExpression("v")),
                new ValueExpression(new IntValue(5)), "+")));

        ArrayList<ProgramState> prg8 = new ArrayList<>();
        prg8.add(new ProgramState(ex8));
        IRepository repo8 = new InMemoryRepository(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        //ninth example
        IStatement ex9 = new CompStatement(new VarDeclarationStatement("v",new IntType()), new CompStatement(
                new AssignStatement("v",new ValueExpression(new IntValue(10))), new WhileStatement(
                new RelationalExpression(new VarExpression("v"),new ValueExpression(new IntValue(0)), ">"),
                new CompStatement(new PrintStatement(new VarExpression("v")),
                        new AssignStatement( "v",new ArithmeticExpression(new VarExpression("v"),
                                new ValueExpression(new IntValue(1)), "-"))))));

        ArrayList<ProgramState> prg9 = new ArrayList<>();
        prg9.add(new ProgramState(ex9));
        IRepository repo9 = new InMemoryRepository(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        //tenth example
        IStatement ex10 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new VarDeclarationStatement("v",
                new ReferenceType(new IntType())), new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(20)))), new VarDeclarationStatement("a",
                new ReferenceType(new ReferenceType(new IntType())))), new NewStatement(new StringValue("a"), new VarExpression("v"))),
                new NewStatement(new StringValue("v"), new ValueExpression(new IntValue(30)))), new PrintStatement(new HeapReadingExpression(
                        new HeapReadingExpression(new VarExpression("a")))));

        ArrayList<ProgramState> prg10 = new ArrayList<>();
        prg10.add(new ProgramState(ex10));
        IRepository repo10 = new InMemoryRepository(prg10, "log10.txt");
        Controller ctr10 = new Controller(repo10);

        //eleventh example
        IStatement ex11 = new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement(new CompStatement
                (new VarDeclarationStatement("v", new IntType()), new VarDeclarationStatement("a", new ReferenceType(new IntType()))),
                new AssignStatement("v", new ValueExpression(new IntValue(10)))), new NewStatement(new StringValue("a"), new ValueExpression(new IntValue(22)))),
                new ForkStatement(new CompStatement(new CompStatement(new CompStatement(new WriteHeapStatement(new StringValue("a"), new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32)))),
                        new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))),
                new PrintStatement(new VarExpression("v"))), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))));

        ArrayList<ProgramState> prg11 = new ArrayList<>();
        ProgramState n = new ProgramState(ex0);
        prg11.add(n);
        IRepository repo11 = new InMemoryRepository(prg11, "log11.txt");
        Controller ctr11 = new Controller(repo11);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
        menu.addCommand(new RunExampleCommand("11", ex11.toString(), ctr11));
        menu.show();
    }
}
*/