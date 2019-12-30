package View;

import Controller.Controller;
import Model.Collection.MyDictionary;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;

public class RunExampleCommand extends Command {
    private Controller ctr;
    RunExampleCommand(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
            ctr.getOriginalProgram().typeCheck(typeEnv);
            System.out.println(typeEnv.toString());
            ctr.allStep(); }
        catch (MyException error) {
            System.out.println(error.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}