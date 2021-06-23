package format;

public class Vector<T> {
    private final T[] vector;

    public Vector(T[] vector) {
        this.vector = vector;
    }

    public int length() {
        return vector.length;
    }

    public T[] get() {
        return vector;
    }
}
