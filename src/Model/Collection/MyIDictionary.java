package Model.Collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T1,T2> {
    boolean isDefined(T1 key);
    T2 getValue(T1 key);
    void update(T1 newT1, T2 newT2);
    T2 lookup(T1 key);
    void remove(T1 key);
    List<T2> values();
    MyIDictionary<T1,T2> deepCopy();
    Iterable<Map.Entry<T1,T2>> getAll();
    Set<Map.Entry<T1, T2>> entrySet();
}
