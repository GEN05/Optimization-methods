import java.util.Collections;

public class Main {

    private static final double EPS = 1E-5;
    //  𝑥𝑠𝑖𝑛(𝑥)+2𝑐𝑜𝑠(𝑥)
    private static final double phi = (1 + Math.sqrt(5)) / 2;
    public static void main(String[] args) {
        double r = -4.0;
        double l = -6.0;
        double x1 = goldSearch(l + (r - l) / (phi + 1), r - (r - l) / (phi + 1), r, l);
        System.out.println(x1 + " " + function(x1));
    }

    private static double function(double x) {
        return x * Math.sin(x) + 2 * Math.cos(x);
    }

    private static double goldSearch(double x1, double x2, double r, double l) {
        double y1 = function(x1);
        double y2 = function(x2);
        if (y1 < y2) {
            r = x2;
            x2 = x1;
            x1 = l + (r - l) / (phi + 1);
        } else {
            l = x1;
            x1 = x2;
            x2 = r - (r - l) / (phi + 1);
        }
        if (Math.abs(r - l) < EPS)
            return ((l + r) / 2);
        else
            return goldSearch(x1, x2, r, l);
    }
}
