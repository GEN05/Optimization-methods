package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.util.Arrays;

public class NewtonDirectionDescent extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps) {
        Vector x = new Vector(start);
        System.out.println(x);
        Vector d;
        Vector s;
        System.out.println(x);
        do {
            Vector g = new Vector(functions.gradientValue(x));
            s = slay(functions.hessianValue(x), Arrays.stream(new Vector(g.getCoordinates()).getCoordinates()).map(y -> -y).toArray());
            d = Vector.multiply(s, g) < 0 ? s : Vector.negative(g);
            s = getVector(functions, x, d);
            System.out.println(x);
        } while (norm(s) >= eps);
        return x;
    }

    private Vector getVector(Functions functions, Vector x, Vector d) {
        double r;
        Vector s;
        r = getLambda(functions, x, d);
        s = Vector.multiply(d, r);
        x.plus(s);
        return s;
    }
}
