package model;

import java.util.ArrayList;

import static model.Function.makeNullCounter;

public abstract class Method {
    protected double left = Function.getLeft();
    protected double right = Function.getRight();
    protected double preciseness = 0.00001;
    protected ArrayList<Double> x;
    protected ArrayList<Double> y;
    protected int n;

    public abstract double calculate();

    public void beginTable() {
        System.out.println();
        System.out.println(getClass().getName());
        System.out.println("\\textnumero{} & left & right & length & $x$ & $f(x)$ \\\\ \\hline");
    }

    public double calculate(double preciseness) {
        this.preciseness = preciseness;
        return calculate();
    }

    public void revert() {
        left = Function.getLeft();
        right = Function.getRight();
        makeNullCounter();
    }
}