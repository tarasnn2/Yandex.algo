package ru.yandex.algo.sprint7.g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class G {

  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint7/g/input03.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int sum = Integer.parseInt(in.readLine());
      final int n = Integer.parseInt(in.readLine());
      String[] line = in.readLine().split(" ");
      int nominals[] = new int[n];

      for (int i = 0; i < n; i++) {
        nominals[i] = Integer.parseInt(line[i]);
      }
      System.out.println(calc(sum, nominals));
    }
  }

  private static int calc(int sum, int[] nominals) {
    int[] dp = new int[sum + 1];
    dp[0] = 1;
    for (int nominal : nominals) {
      for (int currentSum = nominal; currentSum <= sum; currentSum++) {
        dp[currentSum] += dp[currentSum - nominal];
      }
    }
    return dp[sum];
  }

}

