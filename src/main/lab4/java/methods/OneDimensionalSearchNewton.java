package methods;

import util.Functions;
import util.Vector;

import java.util.Arrays;

public class OneDimensionalSearchNewton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps) {
        Vector d, s, x = new Vector(start);
        do {
            System.out.println(x);
            d = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            double r = getLambda(functions, x, d);
            System.out.println("Одномерное значение " + r);
            s = Vector.multiplyOnNumber(d, r);
            x.plus(s);
        } while (norm(s) >= eps);
        return x;
    }
}
