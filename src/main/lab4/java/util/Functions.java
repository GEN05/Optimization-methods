package util;

import java.util.function.Function;

public class Functions {
    private final Function<Vector, Double> function;
    private final Function<Vector, double[]> gradient;
    private final Function<Vector, double[][]> hessian;

    public Functions(Function<Vector, Double> function,
                     Function<Vector, double[]> gradient,
                     Function<Vector, double[][]> hessian) {
        this.function = function;
        this.gradient = gradient;
        this.hessian = hessian;
    }

    public Function<Vector, Double> getFunction() {
        return function;
    }

    public Function<Vector, double[]> getGradient() {
        return gradient;
    }

    public Function<Vector, double[][]> getHessian() {
        return hessian;
    }

    public double functionValue(Vector vector) {
        return function.apply(vector);
    }

    public double[] gradientValue(Vector vector) {
        return gradient.apply(vector);
    }

    public double[][] hessianValue(Vector vector) {
        return hessian.apply(vector);
    }
}
