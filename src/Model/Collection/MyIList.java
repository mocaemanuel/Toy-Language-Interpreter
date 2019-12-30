package Model.Collection;

public interface MyIList<T> {
    boolean isEmpty();
    void add(T element);
    void clear();
    T get(int index);
    int size();
}
