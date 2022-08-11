package ru.yandex.algo.sprint7.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class A {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/a/input02.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int count = Integer.parseInt(in.readLine());
      final int[] history = buildHistory(in.readLine(), count);
      System.out.println(calcMaxValue(history));
    }
  }

  private static int calcMaxValue(int[] history) {
    int maxValue = 0;
    for (int i = 1; i < history.length; i++) {
      if (history[i - 1] < history[i]) {
        maxValue += history[i] - history[i - 1];
      }
    }
    return maxValue;
  }

  private static int[] buildHistory(String line, int count) {
    int[] history = new int[count];
    final String[] s = line.split(" ");
    for (int i = 0; i < count; i++) {
      history[i] = Integer.parseInt(s[i]);
    }
    return history;
  }
}
