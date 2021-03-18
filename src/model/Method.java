package model;

public abstract class Method {
    protected double left = Function.getLeft();
    protected double right = Function.getRight();
    protected double preciseness = 0.0001;

    public abstract double calculate();

    public double calculate(double preciseness) {
        this.preciseness = preciseness;
        return calculate();
    }
}