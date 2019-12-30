package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.ProgramState;
import Model.Types.IType;

public class NoOperationStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() { return new NoOperationStatement(); }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException {
        return null;
    }
}
