package ru.yandex.algo.sprint3.h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class H {
  private static final String SEPARATOR = " ";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/h/input13.txt";
  // private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    test1();
    test2();
    test3();
  }

  private static void execute() throws IOException {
    final String[] elements;

    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int elementsCount = Integer.parseInt(in.readLine());
      elements = in.readLine().split(SEPARATOR);
    }
    final String[] sorted = sort(elements);
    for (String s : sorted) {
      System.out.print(s + " ");
      // System.out.print(s);
    }
  }

  private static String[] sort(String[] elements) {
    boolean isNotSorted = true;
    while (isNotSorted) {
      isNotSorted = false;
      for (int i = 0; i < elements.length; i++) {
        int next = i + 1;
        if (next < elements.length) {
          String iValueString = elements[i];
          String nextValueString = elements[next];
          if (-1 == compare(iValueString, nextValueString)) {
            elements[next] = iValueString;
            elements[i] = nextValueString;
            isNotSorted = true;
          }
        }
      }
    }
    return elements;
  }

  private static int compare(String a, String b) {
    if (a.equals(b)) {
      return 0;
    }
    final String[] aSplinted = a.split("");
    final String[] bSplinted = b.split("");
    if (bSplinted.length < aSplinted.length) {
      return compareInner(bSplinted, aSplinted) == 1 ? -1 : 1;
    } else {
      return compareInner(aSplinted, bSplinted);
    }
  }

  private static int compareInner(String[] lessLength, String[] moreLength) {
    for (int i = 0; i < lessLength.length; i++) {
      final int a = Integer.parseInt(lessLength[i]);
      final int b = Integer.parseInt(moreLength[i]);
      if (lessLength.length == moreLength.length) {
        if (a > b) {
          return 1;
        } else if (a < b) {
          return -1;
        }
      } else { // lessLength < moreLength
        if (a >= b) {
          return 1;
        } else {
          return -1;
        }
      }
    }
    return 0;
  }

  private static void test1() {
    String[] lessLength = new String[] {"8"};
    String[] moreLength = new String[] {"8", "9"};
    assert compareInner(lessLength, moreLength) == -1;
  }

  private static void test2() {
    String[] lessLength = new String[] {"1"};
    String[] moreLength = new String[] {"1", "0"};
    assert compareInner(lessLength, moreLength) == 1;
  }

  private static void test3() {
    String[] lessLength = new String[] {"6", "6"};
    String[] moreLength = new String[] {"6", "8"};
    assert compareInner(lessLength, moreLength) == -1;
  }
}
