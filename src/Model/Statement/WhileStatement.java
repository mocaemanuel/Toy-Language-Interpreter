package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Collection.MyIStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }

    public IExpression getExpression() {
        return expression;
    }

    public void setExpression(IExpression expression) {
        this.expression = expression;
    }

    public IStatement getStatement() {
        return statement;
    }

    public void setStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "while( " + expression.toString() + ") { " + statement.toString() + " }";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue expressionResult;
        expressionResult = expression.evaluate(state.getSymTable(), state.getHeapTable());

        if (! expressionResult.getType().equals(new BoolType()))
            throw new MyException("Result incompatible!");

        if( !((BoolValue)expressionResult).getValue().equals(false)){
            state.getExeStack().push(this);
            statement.execute(state);
        }

        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new WhileStatement(expression,statement);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException {
        IType typexp = expression.typeCheck(typeEnv);
        if (typexp.equals(new IntType()) || typexp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
