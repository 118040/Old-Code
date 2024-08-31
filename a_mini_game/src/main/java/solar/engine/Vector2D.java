package solar.engine;

public class Vector2D {
    public float x = 0;
    public float y = 0;

    public Vector2D() {
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D cloneVector() {
        return new Vector2D(x, y);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D scale(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public float dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }
}
