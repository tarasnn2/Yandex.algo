package ru.yandex.algo.sprint7.f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class F {


  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/f/input03.txt";

  private static int M = 1_000_000_007;

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final String[] s = in.readLine().split(" ");
      final int n = Integer.parseInt(s[0]);
      final int k = Integer.parseInt(s[1]);
      System.out.println(calc(n, k));
    }
  }


  private static int calc(int n, int k) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    for (int i = 1; i < n; i++) {
      for (int j = Math.max(0, i - k); j < i; j++) {
        dp[i] = (dp[i] + dp[j]) % M;
      }
    }
    return dp[n - 1];
  }
}
