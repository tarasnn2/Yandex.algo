package ru.yandex.algo.sprint4.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class A {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/a/input03.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int a = Integer.parseInt(in.readLine());
      final int m = Integer.parseInt(in.readLine());
      final String line = in.readLine();
      System.out.print(hashByHornersMethod(line, a, m));
    }
  }

  // ( a + b * c) % m == (a % m + ((b % m) * (c % m) % m)) % m
  // a^3 mod p = ((a mod p)*(a mod p)*(a mod p)) mod p
  private static long hashByHornersMethod(String line, int a, int m) {
    if (line.isEmpty()) {
      return 0;
    }
    final int firstIndex = 0;
    final int lastIndex = line.length() - 1;
    if (firstIndex == lastIndex) {
      return line.charAt(firstIndex);
    }
    long hash = 0;
    for (int i = 0; i <= lastIndex; i++) {
      hash = (hash * a % m + line.charAt(i)) % m;
    }
    return hash;
  }
}
