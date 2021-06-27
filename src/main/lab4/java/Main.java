import methods.Method;
import methods.newton.Newton;
import methods.newton.NewtonDirectionDescent;
import methods.newton.OneDimensionalSearchNewton;
import methods.quasinewton.BroydenFletcherSheno;
import methods.quasinewton.Powell;
import util.Functions;
import util.Vector;

import java.util.Objects;
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

        double eps = 1E-9D;
        Method method;

        method = new Newton();
        write(data, eps, method);

        method = new OneDimensionalSearchNewton();
        write(data, eps, method);

        method = new NewtonDirectionDescent();
        write(data, eps, method);

        method = new BroydenFletcherSheno();
        write(data, eps, method);

        method = new Powell();
        write(data, eps, method);
    }

    private static void write(Data data, double eps, Method method) {
        Vector res;
        System.out.println(method.getClass().getSimpleName());
        System.out.printf("Начальное приближение: %s%n", data.vector);
        res = method.calculate(data.function, data.vector, eps);
        System.out.printf("Ответ: %s\n%n", res);
    }

    private static final class Data {
        private final Functions function;
        private final Vector vector;

        private Data(Functions function, Vector vector) {
            this.function = function;
            this.vector = vector;
        }

        public Functions function() {
            return function;
        }

        public Vector vector() {
            return vector;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Data) obj;
            return Objects.equals(this.function, that.function) &&
                    Objects.equals(this.vector, that.vector);
        }

        @Override
        public int hashCode() {
            return Objects.hash(function, vector);
        }

        @Override
        public String toString() {
            return "Data[" +
                    "function=" + function + ", " +
                    "vector=" + vector + ']';
        }

    }
}
