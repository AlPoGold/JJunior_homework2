package org.example;

public class Asserter {
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Assertion failed: \nExpected " + expected + ", but found " + actual);
        }
    }
    public static void assertEquals(double expected, double actual) {
        if (expected != actual) {
            throw new AssertionError("Assertion failed: \nExpected " + expected + ", but found " + actual);
        }
    }
}
