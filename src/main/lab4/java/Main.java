import methods.Method;
import methods.Newton;
import util.Functions;
import util.Vector;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Vector, Double> f = x -> (x.get(0) * x.get(0) + x.get(1) * x.get(1) - 1.2 * x.get(0) * x.get(1));
        Function<Vector, double[]> gradient = x -> new double[]{
                (2 * x.get(0) - 1.2 * x.get(1)),
                (2 * x.get(1) - 1.2 * x.get(0))};
        Function<Vector, double[][]> hessian = x -> new double[][]{
                new double[]{2.0, -1.2},
                new double[]{-1.2, 2.0}
        };
        Data data = new Data(new Functions(f, gradient, hessian), new Vector(4, 1));
        Method method;
        System.out.println("начальное приближение: " + data.vector.toString());
        method = new Newton();
        Vector res = method.calculate(data.function, data.vector, 0.000001);
        System.out.println("ответ: " + res);
    }

    private record Data(Functions function, Vector vector) {
    }
}
