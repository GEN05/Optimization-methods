package methods;

import format.Matrix;
import format.Vector;

public abstract class Method {
    protected long iterations = 0L;

    public abstract Vector<Double> solve(Matrix matrix);

    protected void addIteration() {
        iterations++;
    }

    protected long getIterations() {
        return iterations;
    }
}
