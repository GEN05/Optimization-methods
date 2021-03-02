import java.util.Collections;

public class Main {

    private static final double EPS = 1E-5;
    //  𝑥𝑠𝑖𝑛(𝑥)+2𝑐𝑜𝑠(𝑥)

    public static void main(String[] args) {

    }

    private double function(double x) {
        return x * Math.sin(x) + 2 * Math.cos(x);
    }

    private double binarySearch(double left, double right) {
        double medium = (right - left) / 2;
        while (Math.abs(right - left) > EPS) {

        }
    }
}
