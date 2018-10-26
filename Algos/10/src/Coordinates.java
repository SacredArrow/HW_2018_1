public class Coordinates {
    double x;
    double y;

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Coordinates(double a, double b) {
        this.x = a;
        this.y = b;
    }


}
