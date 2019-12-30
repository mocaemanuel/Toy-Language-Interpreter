package Model.Collection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {
    private HashMap<T1, T2> dictionary;

    public MyDictionary(){ dictionary = new HashMap<>(); }

    public MyDictionary(HashMap<T1, T2> dictionary){ this.dictionary = dictionary; }

    @Override
    public boolean isDefined(T1 key){
        for (T1 ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                return true;
        }
        return false;
    }

    @Override
    public T2 getValue(T1 key) {
        for (T1 ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                return dictionary.get(ky);
        }
        return null;
    }

    @Override
    public void update(T1 newT1, T2 newT2){
        dictionary.put(newT1, newT2);
    }

    @Override
    public T2 lookup(T1 key){ return dictionary.get(key);}

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for (T1 key : this.dictionary.keySet()) {
            toReturn.append(key.toString()).append(" --> ");
            toReturn.append(this.dictionary.get(key).toString()).append("\n");
        }
        return toReturn.toString();
    }

    @Override
    public void remove(T1 key){
        for (T1 ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                this.dictionary.remove(ky);
        }
    }

    @Override
    public List<T2> values()

    {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public MyIDictionary<T1,T2> deepCopy(){
        return new MyDictionary<T1,T2>(dictionary);
    }

    @Override
    public  Iterable<Map.Entry<T1,T2>> getAll() {
        return dictionary.entrySet();
    }
}
