package Model.Statement;

import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.Collection.MyException;
import Model.ProgramState;
import Model.Types.IType;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "Print(" +  expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        try{
            state.addOutput(expression.evaluate(state.getSymTable(), state.getHeapTable()));
        }
        catch (MyException error){
            throw new MyException(error.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() { return new PrintStatement(expression); }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
