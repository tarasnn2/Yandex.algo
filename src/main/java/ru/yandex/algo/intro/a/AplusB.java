package ru.yandex.algo.intro.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AplusB {

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String s1 = bufferedReader.readLine();
            String s2 = bufferedReader.readLine();
            //String[] s = line.split(" ");
            //System.out.println(Integer.parseInt(s[0]) + Integer.parseInt(s[1]));
            System.out.println(Integer.parseInt(s1) + Integer.parseInt(s2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
