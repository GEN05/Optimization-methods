package model;

public class SteepestDescent extends Method {
    private final double[][] A;
    private final double[] B;
    private final double C;
    private final Point point;
    private final Methods methods;

    public SteepestDescent(double[][] A, double[] B, double C, Point point, Methods methods) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.point = point;
        this.methods = methods;
    }

    @Override
    public Point calculate() {
        Point current = point, gradient;
        while (true) {
            gradient = Function.gradient(A, B, current);
            if (module(gradient) < preciseness) {
                break;
            }
            double λ = switch (methods.name()) {
                case "parabola" -> getLambdaParabola(gradient, current);
                case "dichotomy" -> getLambdaDichotomy(gradient, current);
                case "fibonacci" -> getLambdaFibonacci(gradient, current);
                case "goldenRatio" -> getLambdaGoldenRatio(gradient, current);
                case "brent" -> getLambdaBrent(gradient, current);
                default -> 0;
            };
            current = calculateNewPoint(current, λ, gradient);
        }
        return current;
    }

    //---P A R A B O L A---\\
    private double getLambdaParabola(Point grad, Point p) {
        double x = 1, prevX = 0, x1 = 0, x2, x3 = 0.01;
        double f1 = simplify(x1, grad, p), f3 = simplify(x3, grad, p), fx;
        x2 = findPoint(x1, x3, f1, f3, grad, p);
        double f2 = simplify(x2, grad, p);
        double EPS = 0.00001;
        while (Math.abs(x - prevX) > EPS) {
            prevX = x;
            x = 0.5 * (x1 + x2 - ((f2 - f1) * (x3 - x2) / (x2 - x1)
                    / ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1))));
            fx = simplify(x, grad, p);
            if (x2 < x) {
                if (f2 < fx) {
                    x3 = x;
                    f3 = fx;
                } else {
                    x1 = x2;
                    f1 = f2;
                    x2 = x;
                    f2 = fx;
                }
            } else {
                if (fx < f2) {
                    x3 = x2;
                    f3 = f2;
                    x2 = x;
                    f2 = fx;
                } else {
                    x1 = x;
                    f1 = fx;
                }
            }
        }
        return (x1 + x3) / 2;
    }

    private double findPoint(double x, double y, double fx, double fy, Point grad, Point p) {
        double z, fz;
        while (true) {
            z = (x + y) / 2;
            fz = simplify(z, grad, p);
            if (fz > fy) {
                x = z;
                fx = fz;
            } else if (fz <= fx && fz <= fy) {
                return z;
            }
            if (fz > fx) {
                y = z;
                fy = fz;
            }
        }
    }

    double simplify(double x, Point grad, Point p) {
        return Function.calculate(A, B, C, calculateNewPoint(p, x, grad));
    }

    //---D I T C H O T O M Y---\\
    public double getLambdaDichotomy(Point grad, Point p) {
        double a = 0, b = 0.01, x1, x2;
        final double DELTA = preciseness / 4;
        while (Math.abs(b - a) > preciseness) {
            x1 = (a + b) / 2 - DELTA;
            x2 = (a + b) / 2 + DELTA;
            if (simplify(x1, grad, p) > simplify(x2, grad, p)) {
                a = x1;
            } else {
                b = x2;
            }
        }
        return (a + b) / 2;
    }

    //---F I B O N A C C I---\\
    public double getLambdaFibonacci(Point grad, Point p) {
        double a = 0, b = 0.01, x1, x2, f1, f2;
        long n = 0;
        while ((0.01) / preciseness >= fibonacci(n + 2)) {
            n += 1;
        }
        x1 = a + fibonacci(n - 2) / fibonacci(n) * (b - a);
        x2 = a + fibonacci(n - 1) / fibonacci(n) * (b - a);
        f1 = simplify(x1, grad, p);
        f2 = simplify(x2, grad, p);
        for (int i = 1; i < n - 1; i++) {
            if (f1 > f2) {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = findPlace(a, b, i, false, n);
                f2 = simplify(x2, grad, p);
            } else {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = findPlace(a, b, i, true, n);
                f1 = simplify(x1, grad, p);
            }
        }
        return (a + b) / 2;
    }

    private double fibonacci(long x) {
        return (1 / Math.sqrt(5)) * (Math.pow((1 + Math.sqrt(5)) / 2, x) - Math.pow((1 - Math.sqrt(5)) / 2, x));
    }

    private double findPlace(double a, double b, int k, boolean isFirst, long n) {
        return isFirst ? a + (fibonacci(n - k - 2) * (b - a)) / fibonacci(n - k) : a + (fibonacci(n - k - 1) * (b - a)) / fibonacci(n - k);
    }

    //---G O L D E N    R A T I O---\\
    public double getLambdaGoldenRatio(Point grad, Point p) {
        final double phi = 1.6180339887;
        double x1, x2, y1, y2, l = 0, r = 0.01;

        x1 = r - ((r - l) / phi);
        x2 = l + ((r - l) / phi);
        y1 = simplify(x1, grad, p);
        y2 = simplify(x2, grad, p);

        while (Math.abs(r - l) > preciseness) {
            if (y1 <= y2) {
                r = x2;
                x2 = x1;
                x1 = r - ((r - l) / phi);
                y2 = y1;
                y1 = simplify(x1, grad, p);
            } else {
                l = x1;
                x1 = x2;
                x2 = l + ((r - l) / phi);
                y1 = y2;
                y2 = simplify(x2, grad, p);
            }
        }
        return (r + l) / 2;
    }

    //---B R E N T---\\
    public double getLambdaBrent(Point grad, Point p) {
        double k = (3 - Math.sqrt(5)) / 2;
        double a, c, x, w, v, fx, fw, fv, d, e, g, u, fu;
        a = 0;
        c = 0.01;
        u = x = w = v = a + k * (c - a);
        fx = fw = fv = simplify(x, grad, p);
        d = e = c - a;
        boolean isParabola;
        double EPS = 0.001;
        while (d > EPS) {
            isParabola = false;
            g = e;
            e = d;
            if (areDifferent(x, w, v) && areDifferent(fx, fw, fv)) { //предполагаем метод парабол
                u = parabola(x, w, v, fx, fw, fv);
                if (u >= a + EPS && u <= c - EPS && Math.abs(u - x) < g / 2) { //метод парабол оптимален
                    d = Math.abs(u - x);
                    isParabola = true;
                }
            }
            if (!isParabola) {
                if (x < (c - a) / 2) {
                    u = x + k * (c - x);
                    d = c - x;
                } else {
                    u = x - k * (x - a);
                    d = x - a;
                }
                if (Math.abs(u - x) < EPS) {
                    u = x + Math.signum(u - x) * EPS;
                }
            }
            fu = simplify(u, grad, p);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    c = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (u >= x) {
                    c = u;
                } else {
                    a = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
        }
        return x;
    }

    private boolean areDifferent(double a, double b, double c) {
        return a != b && a != c && b != c;
    }

    private double parabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        return x2 - 0.5 * (Math.pow(x2 - x1, 2) * (f2 - f3) - Math.pow(x2 - x3, 2) * (f2 - f1)) /
                ((x2 - x1) * (f2 - f3) - (x2 - x3) * (f2 - f1));
    }
}
