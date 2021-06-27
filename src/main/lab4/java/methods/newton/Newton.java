package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.util.Arrays;

public class Newton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps) {
        System.out.println(start);
        Vector x = new Vector(start);
        Vector p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
        while (norm(p) >= eps) {
            x.plus(p);
            p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            System.out.println(x);
        }
        return x;
    }
}
