//package ru.yandex.algo.sprint1.a;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class A {

  public static void main(String[] args) throws IOException {
    // test1();
    // test2();
    // String file = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint1/a/input01.txt";
    String file = "input01.txt";
    try (Scanner scanner = new Scanner(new File(file)); ) {
      scanner.useDelimiter(" ");
      int a = Integer.parseInt(scanner.next());
      int x = Integer.parseInt(scanner.next());
      int b = Integer.parseInt(scanner.next());
      int c = Integer.parseInt(scanner.next().trim());
      log(String.valueOf(calcQuadraticEquation(a, x, b, c)));
    }
  }

  private static int calcQuadraticEquation(int a, int x, int b, int c) {
    return (a * x * x) + (b * x) + c;
  }

  private static void log(String message) {
    System.out.println(message);
  }

  private static void test1() {
    assert calcQuadraticEquation(-8, -5, -2, 7) == -183;
    log("test1 success");
  }

  private static void test2() {
    assert calcQuadraticEquation(8, 2, 9, -10) == 40;
    log("test2 success");
  }
}
