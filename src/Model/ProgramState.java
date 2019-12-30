package Model;

import Model.Collection.*;
import Model.Statement.IStatement;
import Model.Values.IValue;
import Model.Values.StringValue;
import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;
    private IHeap<Integer, IValue> heapTable;
    private int id;
    private static int lastAssigned=0;

    public static int getNewId(){
        lastAssigned++;
        return lastAssigned;
    }

    public ProgramState(MyIStack<IStatement> stack, MyIDictionary<String, IValue> symTable,
                        MyIList<IValue> out, MyIDictionary<StringValue, BufferedReader> fileTable,
                        IHeap<Integer, IValue> heapTable, IStatement program, int id){
        exeStack = stack;
        this.symTable  = symTable;
        this.output = out;
        this.fileTable = fileTable;
        originalProgram = program.deepCopy();
        this.heapTable = heapTable;
        exeStack.push(program);
        this.id =id;
    }

    public ProgramState(MyIStack<IStatement> stack, MyIDictionary<String, IValue> symTable,
                 MyIList<IValue> out, MyIDictionary<StringValue, BufferedReader> fileTable,
                        IHeap<Integer, IValue> heapTable, IStatement program){
        exeStack = stack;
        this.symTable  = symTable;
        this.output = out;
        this.fileTable = fileTable;
        originalProgram = program.deepCopy();
        this.heapTable = heapTable;
        exeStack.push(program);
        id=getNewId();
    }

    public ProgramState(IStatement initialProgram){
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heapTable = new Heap();
        this.originalProgram = initialProgram;
        id = getNewId();
        exeStack.push(originalProgram);
    }

    @Override
    public String toString() {
        return  "ID: " + id + "\n" +
                "*****ExecutionStack*****\n" +
                exeStack.toString() + "\n" +

                "*****SymbolTable*****\n" +
                symTable.toString() + "\n" +

                "*****OutputList*****\n" +
                output.toString() + "\n" +

                "*****FileTable*****\n" +
                fileTable.toString() + "\n" +

                "*****HeapTable*****\n" +
                heapTable.toString() + "\n" +
                "------------------------------------------------------\n";
    }

    public void addOutput(IValue value){ this.output.add(value); }

    public void setExeStack(MyIStack<IStatement> exeStack){ this.exeStack = exeStack; }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable){ this.fileTable = fileTable; }

    public void setSymTable(MyIDictionary<String, IValue> symTable){ this.symTable = symTable; }

    public void setOutput(MyIList<IValue> out){ this.output = out; }

    public void setOriginalProgram(IStatement originalProgram){ this.originalProgram = originalProgram; }

    public void setHeapTable(IHeap<Integer, IValue> newTable){ this.heapTable = newTable;}

    public IHeap<Integer, IValue> getHeapTable(){return this.heapTable;}

    public MyIStack<IStatement> getExeStack(){ return exeStack; }

    public MyIDictionary<String, IValue> getSymTable(){ return symTable; }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){ return this.fileTable; }

    public MyIList<IValue> getOutput(){ return output; }

    public IStatement getOriginalProgram(){ return originalProgram; }

    public Boolean isNotCompleted() { return !exeStack.isEmpty(); }

    public ProgramState oneStepExecution() throws MyException{
        if (exeStack.isEmpty())
            throw new MyException("Exe stack is empty!");
        IStatement currentStatement = exeStack.pop();
        if (currentStatement != null)
            return currentStatement.execute(this);
        return null;
    }

    public int getId(){
        return id;
    }


}
