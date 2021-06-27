package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class Newton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log, BufferedWriter writer) throws IOException {
        if (log) {
            writer.write(start + System.lineSeparator());
        }
        Vector x = new Vector(start);
        Vector p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
        while (norm(p) >= eps && counter < limit) {
            counter++;
            x.plus(p);
            p = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            if (log) {
                writer.write(x + System.lineSeparator());
            }
        }
        return x;
    }
}
