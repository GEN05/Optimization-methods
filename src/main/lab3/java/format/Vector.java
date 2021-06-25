package format;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vector<T> {
    private final T[] values;

    @SafeVarargs
    public Vector(T... values) {
        this.values = values;
    }

    public Vector(int size) {
        Object[] objects = new Object[size];
        Arrays.fill(objects, 0D);
        values = (T[]) objects;
    }

    public Vector(Stream<T> values) {
        this.values = (T[]) values.toArray();
    }


    public int size() {
        return values.length;
    }

    public T get(int index) {
        checkIndex(index);
        return values[index];
    }

    public void set(int index, T value) {
        checkIndex(index);
        values[index] = value;
    }

    private void checkIndex(int index) {
        assert index >= 0 && index < size();
    }

    @Override
    public String toString() {
        return Arrays.stream(values)
                .map(Objects::toString)
                .collect(Collectors.joining("; ", "(", ")"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector<T> vector = (Vector<T>) o;
        return Arrays.equals(values, vector.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
