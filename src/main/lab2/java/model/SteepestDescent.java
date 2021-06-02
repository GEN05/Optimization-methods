package model;

public class SteepestDescent extends Method {
    public SteepestDescent(double[][] A, double[] B, double C, Point point, Methods methods) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.point = point;
        this.methods = methods;
        this.counter = 0L;
    }

    @Override
    public Point calculate() throws Exception {
        Point current = point, gradient;
        while (true) {
            checkLimit();
            gradient = Function.gradient(A, B, current);
            if (module(gradient) < preciseness) {
                break;
            }
            double lambda = switch (methods.name()) {
                case "parabola" -> getLambdaParabola(gradient, current);
                case "dichotomy" -> getLambdaDichotomy(gradient, current);
                case "fibonacci" -> getLambdaFibonacci(gradient, current);
                case "goldenRatio" -> getLambdaGoldenRatio(gradient, current);
                case "brent" -> getLambdaBrent(gradient, current);
                default -> 0D;
            };
            current = calculateNewPoint(current, lambda, gradient);
        }
        System.out.println("Модуль градиента: " + module(gradient));
        return current;
    }

    //---P A R A B O L A---\\
    private double getLambdaParabola(Point grad, Point p) {
        double x = 1D, prevX = 0D, x1 = 0D, x2, x3 = 0.01D;
        double f1 = simplify(x1, grad, p), f3 = simplify(x3, grad, p), fx;
        x2 = findPoint(x1, x3, f1, f3, grad, p);
        double f2 = simplify(x2, grad, p);
        double EPS = 0.00001D;
        while (Math.abs(x - prevX) > EPS) {
            prevX = x;
            x = 0.5D * (x1 + x2 - ((f2 - f1) * (x3 - x2) / (x2 - x1)
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
        return (x1 + x3) / 2D;
    }

    private double findPoint(double x, double y, double fx, double fy, Point grad, Point p) {
        double z, fz;
        while (true) {
            z = (x + y) / 2D;
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
        double a = 0D, b = 0.01D, x1, x2;
        final double δ = preciseness / 4;
        while (Math.abs(b - a) > preciseness) {
            x1 = (a + b) / 2D - δ;
            x2 = (a + b) / 2D + δ;
            if (simplify(x1, grad, p) > simplify(x2, grad, p)) {
                a = x1;
            } else {
                b = x2;
            }
        }
        return (a + b) / 2D;
    }

    //---F I B O N A C C I---\\
    public double getLambdaFibonacci(Point grad, Point p) {
        double a = 0D, b = 0.01D, x1, x2, f1, f2;
        long n = 0;
        while ((0.01D) / preciseness >= fibonacci(n + 2)) {
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
        return (a + b) / 2D;
    }

    private double fibonacci(long x) {
        return (1D / Math.sqrt(5D)) * (Math.pow((1D + Math.sqrt(5D)) / 2D, x) - Math.pow((1D - Math.sqrt(5D)) / 2D, x));
    }

    private double findPlace(double a, double b, int k, boolean isFirst, long n) {
        return isFirst ? a + (fibonacci(n - k - 2) * (b - a)) / fibonacci(n - k) : a + (fibonacci(n - k - 1) * (b - a)) / fibonacci(n - k);
    }

    //---G O L D E N    R A T I O---\\
    public double getLambdaGoldenRatio(Point grad, Point p) {
        final double φ = 1.6180339887D;
        double x1, x2, y1, y2, l = 0D, r = 0.01D;

        x1 = r - ((r - l) / φ);
        x2 = l + ((r - l) / φ);
        y1 = simplify(x1, grad, p);
        y2 = simplify(x2, grad, p);

        while (Math.abs(r - l) > preciseness) {
            if (y1 <= y2) {
                r = x2;
                x2 = x1;
                x1 = r - ((r - l) / φ);
                y2 = y1;
                y1 = simplify(x1, grad, p);
            } else {
                l = x1;
                x1 = x2;
                x2 = l + ((r - l) / φ);
                y1 = y2;
                y2 = simplify(x2, grad, p);
            }
        }
        return (r + l) / 2D;
    }

    //---B R E N T---\\
    public double getLambdaBrent(Point grad, Point p) {
        double k = (3D - Math.sqrt(5D)) / 2D;
        double a, c, x, w, v, fx, fw, fv, d, e, g, u, fu;
        a = 0D;
        c = 0.01D;
        u = x = w = v = a + k * (c - a);
        fx = fw = fv = simplify(x, grad, p);
        d = e = c - a;
        boolean isParabola;
        double ε = 0.001D;
        while (d > ε) {
            isParabola = false;
            g = e;
            e = d;
            if (areDifferent(x, w, v) && areDifferent(fx, fw, fv)) {
                u = parabola(x, w, v, fx, fw, fv);
                if (u >= a + ε && u <= c - ε && Math.abs(u - x) < g / 2D) {
                    d = Math.abs(u - x);
                    isParabola = true;
                }
            }
            if (!isParabola) {
                if (x < (c - a) / 2D) {
                    u = x + k * (c - x);
                    d = c - x;
                } else {
                    u = x - k * (x - a);
                    d = x - a;
                }
                if (Math.abs(u - x) < ε) {
                    u = x + Math.signum(u - x) * ε;
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
        return x2 - 0.5D * (Math.pow(x2 - x1, 2) * (f2 - f3) - Math.pow(x2 - x3, 2) * (f2 - f1)) /
                ((x2 - x1) * (f2 - f3) - (x2 - x3) * (f2 - f1));
    }
}
