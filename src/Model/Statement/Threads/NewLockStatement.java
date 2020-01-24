package Model.Statement.Threads;

import Model.Collection.ILockTable;
import Model.Collection.LockTable;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public NewLockStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        ILockTable lockTable = state.getLockTable();
        MyIDictionary<String, IValue> symbolTable = state.getSymTable();

        Integer location = state.getLockAddress();

        lockTable.update(location, -1);
        symbolTable.update(var, new IntValue(location));

        state.setSymTable(symbolTable);
        state.setLockTable(lockTable);
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!(typeEnv.lookup(var) instanceof IntType)){
            throw new MyException(var + "is not of Int type");
        }
        return null;
    }

    @Override
    public String toString(){
        return "newLock( " + var + " )";
    }
}
