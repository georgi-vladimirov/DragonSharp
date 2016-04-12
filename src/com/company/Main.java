package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numOfLines = input.nextInt();
        List<String> output = new ArrayList<>();
        input.nextLine();
        String regex = "^((if)\\s(\\((\\d+)(==|<|>)(\\d+)\\))\\s(loop)\\s(\\d+)\\s(out)\\s\"(...+)\";)|((if)\\s(\\((\\d+)(==|<|>)(\\d+)\\))\\s(out)\\s\"(.+)\";)|((else)\\s(loop)\\s(\\d+)\\s(out)\\s\"(.+)\";)|((else)\\s(out)\\s\"(.+)\";)$";
        Pattern pattern = Pattern.compile(regex);
        boolean trueStatement = false;
        for (int i = 0; i < numOfLines; i++) {
            String line = input.nextLine();
            String temp = "";
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                int z=0;
                while (matcher.find(z)) {
                    temp = matcher.group();
                    z++;
                }
            }else{
                System.out.printf("Compile time error @ line %d",i+1);
                output.clear();
                break;
            }
            String[] statement = temp.split(" ");
            String text = text(temp);

            if (statement[0].equals("if")) {
                if (statementTest(statement[1])) {
                    if (statement[2].equals("loop")) {
                        int loopCount = Integer.parseInt(statement[3]);
                        for (int j = 0; j < loopCount; j++) {
                            output.add(text);
                        }
                        trueStatement = true;
                    } else {
                        output.add(text);
                        trueStatement = true;
                    }
                } else {
                    trueStatement = false;
                }
            } else {
                if (!trueStatement) {
                    if (statement[1].equals("out")) {
                        output.add(text);
                    } else {
                        int loopCount = Integer.parseInt(statement[2]);
                        for (int j = 0; j < loopCount; j++) {
                            output.add(text);
                        }
                    }
                }
            }
        }
        for (String s : output) {
            System.out.println(s);
        }
    }

    private static boolean statementTest(String str) {
        String[] statement = str.substring(1, str.length() - 1).split("[=<>]+");
        int numOne = Integer.parseInt(statement[0]);
        int numTwo = Integer.parseInt(statement[1]);
        String operator = str.replaceAll("(\\(\\d+)", "").replaceAll("(\\d+\\))", "");
        if (operator.equals("==")) {
            return numOne == numTwo;
        } else if (operator.equals(">")) {
            return numOne > numTwo;
        } else if (operator.equals("<")) {
            return numOne < numTwo;
        }
        return false;
    }
    private static String text(String str){
        Pattern pattern = Pattern.compile("\"(.+)\"");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            return matcher.group(1);
        }
        return "no match";
    }
}
