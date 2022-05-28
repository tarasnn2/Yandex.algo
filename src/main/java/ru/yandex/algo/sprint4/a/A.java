package ru.yandex.algo.sprint4.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class A {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/a/input7.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    final int a;
    final int m;
    final String s;
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.US_ASCII))) {
      a = Integer.parseInt(in.readLine());
      m = Integer.parseInt(in.readLine());
      s = in.readLine();
    }
    System.out.print(hash(a, m, s));
  }
  // ( a + b * c) % m == (a % m + ((b % m) * (c % m) % m)) % m
  private static long hash(int a, int m, String s) {
    int sum = 0;
    for (int i = 0; i < s.length(); i++) {
      final int j = s.charAt(i);
      final int exp = s.length() - (i + 1);
      final long modPowResult = modularPow(a, exp, m);
      sum += (modPowResult * (j % m));
    }
    return sum % m;
  }

  private static long modularPow(int base, int exp, int m) {
    int c = 1;
    for (int i = 1; i <= exp; i++) {
      c = (c * base) % m;
    }
    return c;
  }
}
