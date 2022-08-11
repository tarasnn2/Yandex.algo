package ru.yandex.algo.sprint7.d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class D {


  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/d/input03.txt";

  private static int M = 1000000007;

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int i = Integer.parseInt(in.readLine());

      System.out.println(calc(i));
    }
  }

  private static int calc(int i) {
    if (i == 0) {
      return 1;
    }

    int[] dp = new int[i + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int j = 2; j <= i; j++) {
      dp[j] = (dp[j - 2] + dp[j - 1]) % M;
    }
    return dp[i];
  }

}
