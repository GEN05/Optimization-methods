package model;

import java.util.ArrayList;

public abstract class Method {
    protected double left = Function.getLeft();
    protected double right = Function.getRight();
    protected double preciseness = 0.0001;
    protected ArrayList<Double> x;
    protected ArrayList<Double> y;
    protected int n;

    public abstract double calculate();

    public double calculate(double preciseness) {
        this.preciseness = preciseness;
        return calculate();
    }

    public void revert() {
        left = Function.getLeft();
        right = Function.getRight();
    }
}