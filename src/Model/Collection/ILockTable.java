package Model.Collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ILockTable {
    boolean isDefined(Integer key);
    Integer getValue(Integer key);
    void update(Integer newT1, Integer newT2);
    Integer lookup(Integer key);
    String toString();
    void remove(Integer key);
    Set<Map.Entry<Integer, Integer>> entrySet();
    List<Integer> values();
    LockTable deepCopy();
    Iterable<Map.Entry<Integer, Integer>> getAll();
}
