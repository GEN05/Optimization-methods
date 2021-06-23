package controller;

import model.*;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Выберите интересующий вас метод:
                1: Метод градиентного спуска
                2: Метод наискорейшего спуска
                3: Метод сопряжённых градиентов""");
        double[][] A = new double[][]{
                {6, -5},
                {-5, 6}
        };
        double[] B = new double[]{-7, 2};
        double C = -30;
        Point point = new Point(1, -1);
        short mode = scanner.nextShort();
        short unaryMode = 0;
        if (mode == 2) {
            System.out.println("""
                    Выберите метод одномерного поиска
                    1: Метод парабол
                    2: Метод дихотомии
                    3: Метод Фибоначчи
                    4: Метод золотого сечения
                    5: Комбинированный метод Брента
                    """);
            unaryMode = scanner.nextShort();
        }
        Method method = null;
        switch (mode) {
            case 1:
                method = new GradientDescent(A, B, C, point);
                break;
            case 2:
                switch (unaryMode) {
                    case 1 -> method = new SteepestDescent(A, B, C, point, Methods.parabola);
                    case 2 -> method = new SteepestDescent(A, B, C, point, Methods.dichotomy);
                    case 3 -> method = new SteepestDescent(A, B, C, point, Methods.fibonacci);
                    case 4 -> method = new SteepestDescent(A, B, C, point, Methods.goldenRatio);
                    case 5 -> method = new SteepestDescent(A, B, C, point, Methods.brent);
                    default -> System.err.println("Wrong unary mode");
                }
                break;
            case 3:
                method = new ConjugateGradient(A, B, point);
                break;
        }
        if (method == null) {
            return;
        }
        write(method);
    }

    private void write(Method method) {
        Point answer = null;
        try {
            answer = method.calculate();
            System.out.println(answer);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        method.writeCounter();
        System.out.println("F(Point) = " + Function.function(Objects.requireNonNull(answer)));
    }
}
