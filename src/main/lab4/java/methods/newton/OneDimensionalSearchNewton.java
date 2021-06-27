package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class OneDimensionalSearchNewton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log, BufferedWriter writer) throws IOException {
        Vector d, s, x = new Vector(start);
        do {
            counter++;
            if (log) {
                writer.write(x + System.lineSeparator());
            }
            d = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            double r = getLambda(functions, x, d);
            if (log) {
                writer.write("Одномерное значение " + r + System.lineSeparator());
            }
            s = Vector.multiply(d, r);
            x.plus(s);
        } while (norm(s) >= eps && counter < limit);
        return x;
    }
}
