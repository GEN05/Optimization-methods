package format;

public class CommonMatrix extends Matrix {
    private final Vector<Double>[] matrix;

    public CommonMatrix(Vector<Double>[] matrix) {
        this.matrix = matrix;
    }

    @Override
    protected double getImpl(int i, int j) {
        return matrix[i].get(j);
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        matrix[i].set(j, value);
    }

    @Override
    public int rowsCount() {
        return matrix.length;
    }

    @Override
    public int columnsCount() {
        return matrix[0].size();
    }
}
