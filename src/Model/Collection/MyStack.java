package Model.Collection;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T element){
        stack.push(element);
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        int size = this.stack.size() - 1;
        for (int index = size; index >= 0; index--)
            toReturn.append(this.stack.get(index).toString()).append("\n");
        return toReturn.toString();
    }

    @Override
    public List<T> getAll(){
        return stack;
    }

}
