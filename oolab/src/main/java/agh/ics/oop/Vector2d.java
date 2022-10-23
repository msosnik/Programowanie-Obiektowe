package agh.ics.oop;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public static void main(String[] args) {
//        Vector2d position1 = new Vector2d(1,2);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2,1);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));
//    }
    @Override
    public String toString() {
        return "(" + this.x +", " + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        if (this.x <= other.x && this.y <= other.y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean follows(Vector2d other) {
        if (this.x >= other.x && this.y >= other.y) {
            return true;
        } else {
            return false;
        }
    }
    public Vector2d upperRight(Vector2d other) {
        Vector2d supremum = new Vector2d(max(this.x, other.x), max(this.y, other.y));
        return supremum;
    }

    public Vector2d lowerLeft(Vector2d other) {
        Vector2d infimum = new Vector2d(min(this.x, other.x), min(this.y, other.y));
        return infimum;
    }

    public Vector2d add(Vector2d other) {
        Vector2d sum = new Vector2d(this.x+ other.x, this.y+ other.y);
        return sum;
    }

    public Vector2d subtract(Vector2d other) {
        Vector2d result = new Vector2d(this.x- other.x, this.y- other.y);
        return result;
    }
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Vector2d) {
            Vector2d another = (Vector2d) other;
            if (this.x == another.x && this.y == another.y) {
                return true;
            }
        }
        return false;
    }
}
