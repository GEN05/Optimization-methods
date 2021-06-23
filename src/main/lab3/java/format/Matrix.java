package format;

public class Matrix<T> {
    private final T[][] matrix;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public int length() {
        return matrix.length;
    }

    public T[][] get() {
        return matrix;
    }
}
