package Model.Statement.Threads;

import Model.Collection.ILockTable;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public UnlockStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        IValue foundIndex = state.getSymTable().lookup(var);

        if(foundIndex == null)
            throw new MyException("No such variable in SymbolTable");
        ILockTable lockTable = state.getLockTable();
        Integer lockValue = lockTable.lookup((Integer)foundIndex.getValue());
        if (lockValue == null)
            throw new MyException("No such index in LockTable");
        if (lockValue.equals(state.getId())) {
            lockTable.update((Integer)foundIndex.getValue(), -1);
            state.setLockTable(lockTable);
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(var);
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
        return "unlock( " + var + " )";
    }
}