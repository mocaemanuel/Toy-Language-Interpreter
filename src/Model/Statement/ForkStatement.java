package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Collection.MyStack;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        return new ProgramState(new MyStack<>(), state.getSymTable().deepCopy(), state.getOutput(),
                state.getFileTable(), state.getHeapTable(), statement, state.getNewId(), state.getLockTable());
    }

    @Override
    public IStatement deepCopy() {return new ForkStatement(statement);}

    @Override
    public String toString(){ return "fork( " + statement.toString() + " )";}

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException {
        return statement.typeCheck(typeEnv.deepCopy());
    }
}
