package model;

public class Dichotomy extends Method {
    @Override
    public double calculate() {
        double delta = preciseness / 10;
//        int i = 0;
//        System.out.println("\\textnumero{} & left & right & length & $x$ & $f(x)$ \\\\ \\hline");
        while ((right - left) / 2 > preciseness) {
//            System.out.println(i + " & " + left + " & " + right + " & " + (right - left) + " & " + ((right + left) / 2) + " & " + Function.calculate((right + left) / 2));
//            i++;
            double x1 = (left + right - delta) / 2,
                    x2 = (left + right + delta) / 2;
            if (Function.calculate(x1) > Function.calculate(x2)) left = x1;
            else right = x2;
        }
        return (left + right) / 2;
    }
}
