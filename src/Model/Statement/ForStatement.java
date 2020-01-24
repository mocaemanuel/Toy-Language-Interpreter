package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.*;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.IntType;


public class ForStatement implements IStatement {
    private IExpression init;
    private IExpression cond;
    private IExpression step;
    private IStatement statement;

    public ForStatement(IExpression init, IExpression cond, IExpression step, IStatement stmt){
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.statement = stmt;
    }

    @Override
    public String toString(){
        return "For v = " + init.toString() + "; v < " + cond.toString() + "; v = " + step.toString() + "\n " + statement.toString();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws
            MyException {
        IType typExp= init.typeCheck(typeEnv);
        if(typExp.equals(new IntType()))
        {
            statement.typeCheck(typeEnv);
            return typeEnv;
        }else
        {
            IType typExp1= cond.typeCheck(typeEnv);
            if(typExp1.equals(new IntType()))
            {
                statement.typeCheck(typeEnv);
                return typeEnv;
            }
            else {
                IType typExp2= step.typeCheck(typeEnv);
                if(typExp2.equals(new IntType()))
                {
                    statement.typeCheck(typeEnv);
                    return typeEnv;
                }
                else throw new MyException("incompatible type - error!");
            }
        }
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        IStatement aux = new CompStatement(new CompStatement(new VarDeclarationStatement("v", new IntType()),
                new AssignStatement("v", init)),
                new WhileStatement(new RelationalExpression( new VarExpression("v"), cond, "<"), new CompStatement(statement,
                        new AssignStatement("v", step))));

        state.getExeStack().push(aux);
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new ForStatement(init, cond, step, statement);
    }
}

