package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Collection.MyIStack;
import Model.ProgramState;
import Model.Types.IType;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement newFirst, IStatement newSecond){
        this.first = newFirst;
        this.second = newSecond;
    }

    @Override
    public String toString(){
        return "" + first.toString() + ";"  + second.toString() + "";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);

        return null;
    }

    @Override
    public IStatement deepCopy() { return new CompStatement(first, second); }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        return second.typeCheck(first.typeCheck(typeEnv));
    }

}
