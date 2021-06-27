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

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {
        Data data;
        for (int i = 6; i <= 7; i++) {
            data = getData(i);
            System.out.println("=================================================");
            double eps = 1E-7D;
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
    }

    private static Data getData(int mode) {
        Function<Vector, Double> function = null;
        Function<Vector, double[]> gradient = null;
        Function<Vector, double[][]> hessian = null;
        Vector initialApproximation = null;
        switch (mode) {
            case 1 -> {
                function = x -> (x.get(0) * x.get(0) + x.get(1) * x.get(1) - 1.2 * x.get(0) * x.get(1));
                gradient = x -> new double[]{
                        (2 * x.get(0) - 1.2 * x.get(1)),
                        (2 * x.get(1) - 1.2 * x.get(0))};
                hessian = x -> new double[][]{
                        new double[]{2.0, -1.2},
                        new double[]{-1.2, 2.0}
                };
                initialApproximation = new Vector(4, 1);
            }
            case 2 -> {
                function = x -> (100 * pow(x.get(0), 4) +
                        100 * pow(x.get(1), 2) - 200 * pow(x.get(0), 2) * x.get(1) +
                        pow(x.get(0), 2) - 2 * x.get(0) + 1);
                gradient = x -> new double[]{
                        400 * pow(x.get(0), 3) - 400 * x.get(0) * x.get(1) + 2 * x.get(0) - 2,
                        200 * x.get(1) - 200 * pow(x.get(0), 2)
                };
                hessian = x -> new double[][]{
                        new double[]{1200 * pow(x.get(0), 2) - 400 * x.get(1) + 2, -400 * x.get(0)},
                        new double[]{-400 * x.get(0), 200.0}
                };
                initialApproximation = new Vector(-1.2, 1);
            }
            case 3 -> {
                function = x -> (pow(pow(x.get(0), 2) + x.get(1) - 11, 2) +
                        pow(x.get(0) + pow(x.get(1), 2) - 7, 2));
                gradient = x -> new double[]{
                        4 * (pow(x.get(0), 3) + x.get(0) * x.get(1) - 11 * x.get(0)) + 2 * (x.get(0) + pow(x.get(1), 2) - 7),
                        2 * (pow(x.get(0), 2) + x.get(1) - 11) + 4 * (x.get(0) * x.get(1) + pow(x.get(1), 3) - 7 * x.get(1))
                };
                hessian = x -> new double[][]{
                        new double[]{12 * pow(x.get(0), 2) + 4 * x.get(1) - 42, 4 * (x.get(0) + x.get(1))},
                        new double[]{4 * (x.get(0) + x.get(1)), 4 * x.get(0) + 12 * pow(x.get(1), 2) - 26}
                };
                initialApproximation = new Vector(1, 1);
            }
            case 4 -> {
                function = x -> (pow(x.get(0) + 10 * x.get(1), 2) + 5 * pow(x.get(2) - x.get(3), 2) +
                        pow(x.get(1) - 2 * x.get(2), 4) + 10 * pow(x.get(0) - x.get(3), 4));
                gradient = x -> new double[]{
                        2 * (20 * pow(x.get(0) - x.get(3), 3) + x.get(0) + 10 * x.get(1)),
                        4 * (5 * (x.get(0) + 10 * x.get(1)) + pow(x.get(1) - 2 * x.get(2), 3)),
                        10 * (x.get(2) - x.get(3)) - 8 * pow(x.get(1) - 2 * x.get(2), 3),
                        10 * (-4 * pow(x.get(0) - x.get(3), 3) - x.get(2) + x.get(3))
                };
                hessian = x -> new double[][]{
                        new double[]{120 * pow(x.get(0) - x.get(3), 2) + 2, 20, 0, -120 * pow(x.get(0) - x.get(3), 2)},
                        new double[]{20, 200 + 12 * pow((x.get(1) - 2 * x.get(2)), 2), -24 * pow(x.get(1) - 2 * x.get(2), 2), 0},
                        new double[]{0, -24 * pow(x.get(1) - 2 * x.get(2), 2), 10 + 48 * (x.get(1) - 2 * x.get(2)), -10},
                        new double[]{-120 * pow(x.get(0) - x.get(3), 2), 0, -10, 120 * pow(x.get(0) - x.get(3), 2) + 10}
                };
                initialApproximation = new Vector(1, 1, 1, 1);
            }
            case 5 -> {
                function = x -> (-2.0 / (0.25 * pow(x.get(0) - 1, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1) - 1.0 / (0.25 * pow(x.get(0) - 2, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1) + 100);
                gradient = x -> new double[]{
                        (648 * (x.get(0) - 2)) / pow(9 * pow(x.get(0), 2) - 36 * x.get(0) + 4 * pow(x.get(1), 2) - 8 * x.get(1) + 76, 2) + (x.get(0) - 1) / pow(0.25 * pow(x.get(0) - 1, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 2),
                        2.0 / 9 * (x.get(1) - 1) * (2.0 / pow(0.25 * pow(x.get(0) - 1, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 2) + 1.0 / pow(0.25 * pow(x.get(0) - 2, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 2))
                };
                hessian = x -> {
                    double v = 4.0 / 9 * (-1 + x.get(1)) * (-(-1 + x.get(0)) / pow(1 + 1.0 / 4 * pow(-1 + x.get(0), 2) + 1.0 / 9 * pow(-1 + x.get(1), 2), 3) - (23328 * (-2 + x.get(0))) /
                            pow(76 - 36 * x.get(0) + 9 * x.get(0) * x.get(0) - 8 * x.get(1) + 4 * x.get(1) * x.get(1), 3));
                    return new double[][]{
                            new double[]{(-pow(-1 + x.get(0), 2) / pow(1 + 0.25 * pow(-1 + x.get(0), 2) + 1.0 / 9 * pow(-1 + x.get(1), 2), 3) +
                                    1.0 / pow(1 + 0.25 * pow(-1 + x.get(0), 2) + 1.0 / 9 * pow(-1 + x.get(1), 2), 2) - (23328 * pow(-2 + x.get(0), 2)) / pow(76 - 36 * x.get(0) + 9 * x.get(0) * x.get(0) -
                                    8 * x.get(1) + 4 * x.get(1) * x.get(1), 3) + 648 / pow(76 - 36 * x.get(0) + 9 * x.get(0) * x.get(0) - 8 * x.get(1) + 4 * x.get(1) * x.get(1), 2)),
                                    v
                            },
                            new double[]{v,
                                    2.0 / 9 * (4.0 / 9 * pow(x.get(1) - 1, 2) * (-2.0 / pow(1.0 / 4 * pow(x.get(0) - 1, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 3) - 1.0 /
                                            pow(1.0 / 4 * pow(x.get(0) - 2, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 3)) + 1.0 / pow(1.0 / 4 * pow(x.get(0) - 2, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 2) +
                                            2.0 / pow(1.0 / 4 * pow(x.get(0) - 1, 2) + 1.0 / 9 * pow(x.get(1) - 1, 2) + 1, 2))
                            }
                    };
                };
                initialApproximation = new Vector(1, 1);
            }
            case 6 -> {     //  5x^2+2xy +4y^2-3x-8y
                function = x -> (5 * pow(x.get(0), 2) + 2 * x.get(0) * x.get(1) + 4 * pow(x.get(1), 2) - 3 * x.get(0) - 8 * x.get(1));
                gradient = x -> new double[]{
                        (10 * x.get(0) + 2 * x.get(1) - 3),
                        (2 * x.get(0) + 8 * x.get(1) - 8)};
                hessian = x -> new double[][]{
                        new double[]{10, 2},
                        new double[]{2, 8}
                };
                initialApproximation = new Vector(7, -9);
            }
            case 7 -> {     //  -x*sqrt(y)+7*y^2+2x+13y
                function = x -> (-x.get(0) * sqrt(x.get(1)) + 7 * pow(x.get(1), 2) + 2 * x.get(0) + 13 * x.get(1));
                gradient = x -> new double[]{
                        -Math.sqrt(x.get(1)) + 2,
                        -x.get(0) / (2 * Math.sqrt(x.get(1))) + 14 * x.get(1) + 13};
                hessian = x -> new double[][]{
                        new double[]{0, -1 / (2 * Math.sqrt(x.get(1)))},
                        new double[]{-1 / (2 * Math.sqrt(x.get(1))), x.get(0) / (4 * x.get(1) * Math.sqrt(x.get(1))) + 14}
                };
                initialApproximation = new Vector(3, -2);
            }
        }
        return new Data(new Functions(function, gradient, hessian), initialApproximation);
    }

    private static void write(Data data, double eps, Method method) {
        Vector res;
        System.out.println(method.getClass().getSimpleName());
        System.out.printf("Начальное приближение: %s%n", data.vector);
        res = method.calculate(data.function, data.vector, eps, false);
        System.out.printf("Ответ: %s%n", res);
        System.out.printf("Количество итераций: %s\n%n", method.getCounter());
    }

    private record Data(Functions function, Vector vector) {

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
