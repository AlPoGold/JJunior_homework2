package org.example;

import static java.lang.Integer.sum;

public class TestClass {
    public static void main(String[] args) {
        TestRunner.run(TestClass.class);

        int expected = 11;
        int actual = sum(5,8);
        try{
            Asserter.assertEquals(actual, expected);

        }catch (AssertionError e){
            System.out.println(e.getMessage());
        }

        double expected2 = 2.5;
        double actual2 = div(5,0);
        try{
            Asserter.assertEquals(actual2, expected2);

        }catch (AssertionError e){
            System.out.println(e.getMessage());
        }



    }
      @Test(order = 3)
    void test1() {
        System.out.println("test1");
    }

      @Test(order = 1)
    void test2() {
        System.out.println("test2");
    }


      @Test
    void test3() {
        System.out.println("test3");
    }


    @BeforeAll
    void beforeAll(){
        System.out.println("before all");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @AfterAll
    void afterAll() {
        System.out.println("after all");
    }


    @BeforeEach
    void beforeEach(){
        System.out.println("before each");
    }

    static int sum(int a, int b){
        return a+b;
    }
    static double div(int a, int b){
        return (double)a/b;
    }

}
