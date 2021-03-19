import model.*;
import view.Window;

public class Main {
    public static void main(String[] args) {
        Dichotomy dichotomy = new Dichotomy();
        Parabola parabola = new Parabola();
        Fibonacci fibonacci = new Fibonacci();
        GoldenRatio goldenRatio = new GoldenRatio();
        Brent brent = new Brent();
        System.out.println(dichotomy.calculate());
        parabola.revert();
        System.out.println(parabola.calculate());
        fibonacci.revert();
        System.out.println(fibonacci.calculate());
        goldenRatio.revert();
        System.out.println(goldenRatio.calculate());
        brent.revert();
        System.out.println(brent.calculate());
        new Window();
    }
}
