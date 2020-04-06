package sample;

public class Shape {

    private Point x1;
    private Point x2;
    private Point x3;
    private Point x4;

    public Shape() {
    }

    public Shape(Point x1, Point x2, Point x3, Point x4) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }

    //Частный случай
    public static boolean isDotInSide(Shape shape, double x, double y) {
        return shape.x1.x <= x && shape.x1.y >= y
                && shape.x2.x <= x && shape.x2.y <= y
                && shape.x3.x >= x && shape.x3.y <= y
                && shape.x4.x >= x && shape.x4.y >= y;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", x3=" + x3 +
                ", x4=" + x4 +
                '}';
    }

    static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
