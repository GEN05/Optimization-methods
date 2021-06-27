package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.util.Arrays;

public class Newton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log) {
        if (log) {
            System.out.println(start);
        }
        Vector x = new Vector(start);
        Vector p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
        while (norm(p) >= eps && counter < limit) {
            counter++;
            x.plus(p);
            p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            if (log) {
                System.out.println(x);
            }
        }
        return x;
    }
}
