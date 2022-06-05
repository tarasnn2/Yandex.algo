package ru.yandex.algo.sprint4.e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class E1 {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/e/input02.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final String str;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.US_ASCII))) {
      str = in.readLine();
    }
    final int[] letterToPos = new int[256];
    int result = 0;
    int prev = 0;
    for (int i = 0; i < str.length(); i++) {
      final int c = str.charAt(i);
      prev = Math.max(prev, letterToPos[c]);
      letterToPos[c] = i + 1;
      result = Math.max(result, i + 1 - prev);
    }
    System.out.print(result);
  }
}
