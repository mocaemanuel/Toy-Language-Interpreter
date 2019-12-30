package Model.Collection;

import Model.Values.IValue;
import Model.Values.IntValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<I extends Number, V> implements IHeap<Integer, IValue> {
    private Integer nextFreeMemory;
    private HashMap<Integer, IValue> table;

    public Heap(){
        this.table = new HashMap<Integer, IValue>();
        this.nextFreeMemory = 1;
        table.put(0, null);
    }

    @Override
    public boolean isDefined(Integer id) {
        return  table.containsKey(id);
    }

    @Override
    public void update(Integer id, IValue value) {
        table.put(id, value);
    }

    @Override
    public void update(IValue value) {
        table.put(nextFreeMemory, value);
        nextFreeMemory++;
    }

    @Override
    public void remove(IValue value) {
        table.remove(value);
    }

    @Override
    public Integer getCurrentFree() {
        return nextFreeMemory;
    }

    @Override
    public Set<Map.Entry<Integer, IValue>> getEntrySet() {
        return this.table.entrySet();
    }

    @Override
    public IValue getValue(Integer key) {
        return table.get(key);
    }

    @Override
    public void setContent(Map newContent) {
        this.table = (HashMap) newContent;
    }

    @Override
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        for (Integer key : this.table.keySet()) {
            toReturn.append(key.toString()).append(" --> ");
            if (this.table.get(key) == null)
                toReturn.append("null").append("\n");
            else
                toReturn.append(this.table.get(key).toString()).append("\n");
        }
        return toReturn.toString();
    }
}