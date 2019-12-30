package Model.Collection;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList(){
        list = new ArrayList<>();
    }


    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void add(T element){ list.add(element); }

    @Override
    public void clear(){
        list.clear();
    }

    @Override
    public T get(int index){
        return list.get(index);
    }

    @Override
    public int size(){ return list.size(); }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        int size = this.list.size() - 1;
        for (int index = 0; index <= size; index++)
            toReturn.append(this.list.get(index).toString()).append("\n");
        return toReturn.toString();
    }
}
