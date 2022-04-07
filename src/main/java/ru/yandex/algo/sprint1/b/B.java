//package ru.yandex.algo.sprint1.b;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class B {

  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/b/input.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) {
/*    test1();
    test2();
    test3();
    test4();
    test5();*/

    work();
  }

  private static void work() {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE), StandardCharsets.UTF_8))) {
      String[] s = in.readLine().split(" ");
      int a = Integer.parseInt(s[0]);
      int b = Integer.parseInt(s[1]);
      int c = Integer.parseInt(s[2]);
      log(isSameParity(a, b, c) ? "WIN" : "FAIL");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isSameParity(int a, int b, int c) {
    boolean r1 = a % 2 == 0;
    boolean r2 = b % 2 == 0;
    boolean r3 = c % 2 == 0;
    return (r1 && r2 && r3) || (!r1 && !r2 && !r3);
  }

  private static void log(String message) {
    System.out.println(message);
  }

  private static void test1() {
    assert !isSameParity(1, 2, -3);
    log("test1 success");
  }

  private static void test2() {
    assert isSameParity(7, 11, 7);
    log("test2 success");
  }

  private static void test3() {
    assert isSameParity(6, -2, 0);
    log("test3 success");
  }

  private static void test4() {
    assert isSameParity(7, 11, 7);
    log("test4 success");
  }

  private static void test5() {
    assert isSameParity(21, 19, -25);
    log("test5 success");
  }
}
