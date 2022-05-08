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
    // test();
    execute();
  }

  private static void execute() throws IOException {
    final String[] elements;
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int elementsCount = Integer.parseInt(in.readLine());
      elements = in.readLine().split(SEPARATOR);
    }
    final String[] sorted = sort(elements);
    StringBuilder result = new StringBuilder(sorted.length);
    for (String s : sorted) {
      // result.append(s).append("|");
      result.append(s);
    }
    System.out.print(result);
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
    int a = -1; // всегда будет первая итерация и -1 затрется
    for (int i = 0; i < moreLength.length; i++) {
      final int b = Integer.parseInt(moreLength[i]);

      if (lessLength.length == moreLength.length) {
        a = Integer.parseInt(lessLength[i]);
        if (a < b) {
          return -1;
        } else if (a > b) {
          return 1;
        }
      } else { // lessLength < moreLength
        if (i < lessLength.length) {
          a = Integer.parseInt(lessLength[i]);
          if (a < b) {
            return -1;
          } else if (a > b) {
            return 1;
          }
        } else if (b == 0) {
          return 1;
        } else if (b > a) {
          return -1;
        } else {
          return 1;
        }
      }
    }
    return 0;
  }

  private static void test() {

    assert compare("8", "88") == 1;
    assert compare("88", "8") == -1;

    assert compare("1", "10") == 1;
    assert compare("10", "1") == -1;

    assert compare("66", "68") == -1;
    assert compare("68", "66") == 1;

    assert compare("89", "89") == 0;

    assert compare("89000", "89") == -1;

    assert compare("8", "89") == -1;
    assert compare("89", "8") == 1;
  }
}
