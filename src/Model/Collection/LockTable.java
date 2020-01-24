package Model.Collection;

import java.util.*;

public class LockTable implements ILockTable {
    private HashMap<Integer, Integer> dictionary;

    public LockTable(){ dictionary = new HashMap<>(); }

    public LockTable(HashMap<Integer, Integer> dictionary){ this.dictionary = dictionary; }

    @Override
    public boolean isDefined(Integer key){
        for (Integer ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                return true;
        }
        return false;
    }

    @Override
    public Integer getValue(Integer key) {
        for (Integer ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                return dictionary.get(ky);
        }
        return null;
    }

    @Override
    synchronized public  void update(Integer newT1, Integer newT2){
        dictionary.put(newT1, newT2);
    }

    @Override
    synchronized public Integer lookup(Integer key){ return dictionary.get(key);}

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for (Integer key : this.dictionary.keySet()) {
            toReturn.append(key.toString()).append(" --> ");
            toReturn.append(this.dictionary.get(key).toString()).append("\n");
        }
        return toReturn.toString();
    }

    @Override
    public void remove(Integer key){
        for (Integer ky : this.dictionary.keySet()) {
            if (ky.toString().equals(key.toString()))
                this.dictionary.remove(ky);
        }
    }

    @Override
    public Set<Map.Entry<Integer, Integer>> entrySet() {
        return dictionary.entrySet();
    }

    @Override
    public List<Integer> values()

    {
        return new LinkedList<>(this.dictionary.values());
    }

    @Override
    public LockTable deepCopy(){
        return new LockTable(dictionary);
    }

    @Override
    public  Iterable<Map.Entry<Integer, Integer>> getAll() {
        return dictionary.entrySet();
    }
}
