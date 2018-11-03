package org.src;
public class Coordinates {
    double x;
    double y;

    public Coordinates(double a, double b) {
        this.x = a;
        this.y = b;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
