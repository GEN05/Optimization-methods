package model;

public class Brent extends Method {
    @Override
    public double calculate() {
        beginTable();
        double c = (3 - Math.sqrt(5)) / 2,
                d = 0, e = 0,
                eps = Math.sqrt(1.2e-5),
                xm = (left + right) / 2,
                v = left + c * (right - left),
                w = v, x = v,
                fx = Function.calculate(x, left, right),
                fv = fx, fw = fx,
                tol3 = preciseness / 3,
                tol1 = eps * Math.abs(x) + tol3,
                t2 = 2 * tol1,
                p, q, r, u, fu;

        while (Math.abs(x - xm) > (t2 - (right - left) / 2)) {
            p = q = r = 0;

            if (Math.abs(e) > tol1) {
                r = (x - w) * (fx - fv);
                q = (x - v) * (fx - fw);
                p = (x - v) * q - (x - w) * r;
                q = 2 * (q - r);

                if (q > 0) {
                    p = -p;
                } else {
                    q = -q;
                }

                r = e;
                e = d;
            }

            if ((Math.abs(p) < Math.abs(q * r / 2)) && (p > q * (left - x)) && (p < q * (right - x))) {
                d = p / q;
                u = x + d;

                if (((u - left) < t2) || ((right - u) < t2)) {
                    d = tol1;

                    if (x >= xm) {
                        d = -d;
                    }
                }
            } else {
                e = (x < xm ? right : left) - x;

                d = c * e;
            }

            u = x + (Math.abs(d) >= tol1 ? d : (d > 0 ? tol1 : -tol1));

            fu = Function.calculate(u, left, right);

            if (fu <= fx) {
                if (u < x) {
                    right = x;
                } else {
                    left = x;
                }

                v = w;
                fv = fw;

                w = x;
                fw = fx;

                x = u;
                fx = fu;
            } else {
                if (u < x) {
                    left = u;
                } else {
                    right = u;
                }

                if ((fu <= fw) || (w == x)) {
                    v = w;
                    fv = fw;

                    w = u;
                    fw = fu;
                } else if ((fu > fv) && (v == x) && (v == w)) {
                    v = u;
                    fv = fu;
                }
            }

            xm = (left + right) / 2;
            tol1 = eps * Math.abs(x) + tol3;
            t2 = 2 * tol1;
        }

        return x;
    }
}
