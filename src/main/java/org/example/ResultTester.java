package org.example;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultTester {
    private String results;

    private List<Method> successfulTests;
    private List<Method> failedTests;

    public ResultTester() {
        successfulTests = new ArrayList<>();
        failedTests = new ArrayList<>();
    }

    public int getCountTests() {
        return successfulTests.size() + failedTests.size();
    }


    public String getResults() {
        return results;
    }


    public void printResults(){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------").append("\n");
        sb.append("Total count of tests:").append(getCountTests()).append("\n").append("\n");
        sb.append("Success in : ").append(successfulTests.size()).append(" tests").append("\n");
        successfulTests.forEach(x -> {
            sb.append(x.getName()).append("\n");
        });
        sb.append("\n");
        sb.append("Failed in : ").append(failedTests.size()).append(" tests").append("\n");

        failedTests.forEach(x -> {
            sb.append(x.getName()).append("\n");
        });
        sb.append("-----------------------------------------").append("\n");
        results = sb.toString();
        System.out.println(sb);
    }


    public void addSuccessfulTest(Method method){
        successfulTests.add(method);
    }
    public void addFailedTest(Method method){
        failedTests.add(method);
    }
}
