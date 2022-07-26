// 69500235
package ru.yandex.algo.sprint7.final1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Имплементация алгоритма Вагнера — Фишера. В d храним результат сравнения для каждой итерации {i,j} опираясь на предыдущие вычисления.
 * Результат появится в d[I,J].
 * <p>
 * Сложность по вычислению: O(n*m). Сложность по памяти: O(n*m)
 */

public class LevenshteinDistance {

  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/final1/input11.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final String[] s = in.readLine().split("");
      final String[] t = in.readLine().split("");
      int result;
      if (s.length == 0 && t.length == 0) {
        result = 0;
      } else if (s.length == 0) {
        result = t.length;
      } else if (t.length == 0) {
        result = s.length;
      } else {
        result = calc(s, t);
      }
      System.out.println(result);
    }
  }

  private static int calc(String[] s, String[] t) {
    int[][] d = new int[s.length + 1][t.length + 1];
    for (int j = 0; j <= t.length; j++) {
      d[0][j] = j;
    }
    for (int i = 1; i <= s.length; i++) {
      d[i][0] = i;
      for (int j = 1; j <= t.length; j++) {
        final int k = s[i - 1].equals(t[j - 1]) ? 0 : 1;
        d[i][j] = Math.min(
            Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1),
            d[i - 1][j - 1] + k
        );
      }
    }
    return d[s.length][t.length];
  }
}