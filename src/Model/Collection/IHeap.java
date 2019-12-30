package Model.Collection;

import Model.Values.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IHeap<T,V> {

    boolean isDefined(T id);
    void update(Integer id, IValue value);
    void update(V value);
    void remove (IValue value);
    T getCurrentFree();
    Set<Map.Entry<T,V>> getEntrySet();
    V getValue(T key);
    void setContent(Map<Integer,IValue> newContent);

}
