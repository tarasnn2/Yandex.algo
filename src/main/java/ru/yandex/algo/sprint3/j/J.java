package ru.yandex.algo.sprint3.j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class J {
  private static final String SEPARATOR = " ";

  private static final String DELIMITER = "\n";

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint3/j/input01.txt";
  // private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final String[] elements;

    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int elementsCount = Integer.parseInt(in.readLine());
      elements = in.readLine().split(SEPARATOR);
    }
    final StringBuilder result = sort(elements);
    System.out.println(result);
  }

  private static StringBuilder sort(String[] elements) {
    StringBuilder resultAll = new StringBuilder();
    boolean isNotSorted = true;
    int counter = 0;
    while (isNotSorted) {
      StringBuilder result = new StringBuilder();
      isNotSorted = false;

      for (int i = 0; i < elements.length; i++) {
        int next = i + 1;
        if (next < elements.length) {
          String iValueString = elements[i];
          String nextValueString = elements[next];
          int iValue = Integer.parseInt(iValueString);
          int nextValue = Integer.parseInt(nextValueString);
          if (1 == compare(iValue, nextValue)) {
            elements[next] = iValueString;
            elements[i] = nextValueString;
            isNotSorted = true;
          }
        }
        result.append(elements[i]).append(SEPARATOR);
      }
      if (isNotSorted || counter == 0) {
        resultAll.append(result).append(DELIMITER);
      }
      counter++;
    }

    return resultAll;
  }

  private static int compare(int a, int b) {
    return Integer.compare(a, b);
  }
}
