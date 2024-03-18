package org.example;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static int countTests;


    public static void run(Class<?> testClass) {

        ResultTester resultTester = new ResultTester();
        Map<Integer, List<Method>> orderedMethods = getOrderedListOfMethods(testClass.getDeclaredMethods());

        final Object testObj = initTestObj(testClass);

        for (List<Method> methods : orderedMethods.values()) {
            for (Method method : methods) {
                try{
                    invokeMethod(method, testObj);
                    resultTester.addSuccessfulTest(method);
                }catch(RuntimeException e){
                    resultTester.addFailedTest(method);
                }

            }
        }

        resultTester.printResults();
    }

    private static void invokeMethod(Method method, Object testObj) {
        try {
            method.setAccessible(true);
            method.invoke(testObj);
        } catch (IllegalAccessException | InvocationTargetException e) {

            throw new RuntimeException(e);
        }
    }




    private static Map<Integer, List<Method>> getOrderedListOfMethods(Method[] methods) {
        Map<Integer, List<Method>> orderedMethods = new TreeMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                int order = method.getAnnotation(Test.class).order();
                if (!orderedMethods.containsKey(order)) {
                    orderedMethods.put(order, new ArrayList<>());
                }
                orderedMethods.get(order).add(method);
            }
        }

        orderedMethods.forEach((key, value) -> System.out.println(key +" "+ value));

        for(Method method:methods) {

            if (method.isAnnotationPresent(BeforeEach.class)) {
                for (List<Method> list : orderedMethods.values()) {
                    int size = list.size();
                    for (int i = size - 1; i >= 0; i--) {
                        list.add(i, method);
                    }
                }
            }
        }
        for(Method method:methods) {
            if (method.isAnnotationPresent(AfterEach.class)) {
                for (Integer order: orderedMethods.keySet()) {
                    List<Method> listInit = orderedMethods.get(order);
                    List<Method> newList = new ArrayList<>();
                    for (int i = 0; i < listInit.size(); i+=2) {
                        newList.add(listInit.get(i));
                        newList.add(listInit.get(i+1));
                        newList.add(method);

                    }
                    orderedMethods.put(order, newList);
                }
            }
        }


        for(Method method : methods){
            if(method.isAnnotationPresent(BeforeAll.class)){
                orderedMethods.put(-1, new ArrayList<>());
                orderedMethods.get(-1).add(method);
            }
            if(method.isAnnotationPresent(AfterAll.class)){
                orderedMethods.
                        put(orderedMethods.size(), new ArrayList<>(Collections.singleton(method)));
            }
        }
        countTests = orderedMethods.values().size();
        return orderedMethods;
    }

    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }


}
