package methods;

import util.Functions;
import util.Vector;

import java.util.Arrays;

public class Newton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector x0, double eps) {
        System.out.println(x0);
        Vector x = new Vector(x0);
        Vector p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
        while (norm(p) >= eps) {
            x.plus(p);
            p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            System.out.println(x);
        }
        return x;
    }
}
