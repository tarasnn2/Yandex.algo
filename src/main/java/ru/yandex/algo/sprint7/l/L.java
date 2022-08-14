package ru.yandex.algo.sprint7.l;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class L {

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/l/input02.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      String[] line = in.readLine().split(" ");
      final int count = Integer.parseInt(line[0]);
      final int capacity = Integer.parseInt(line[1]);

      line = in.readLine().split(" ");
      int weights[] = new int[count];

      for (int i = 0; i < count; i++) {
        weights[i] = Integer.parseInt(line[i]);
      }
      System.out.println(calc(capacity, weights));
    }
  }

  private static int calc(int capacity, int[] weights) {
    int[] dp = new int[capacity + 1];
    dp[0] = 1;
    int max = 0;
    for (int weight : weights) {
      for (int i = capacity; i >= weight; --i) {
        if (dp[i - weight] == 1) {
          dp[i] = 1;
          max = Math.max(max, i);
        }
      }
    }
    return max;
  }

}

