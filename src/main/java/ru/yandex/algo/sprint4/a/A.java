//package ru.yandex.algo.sprint4.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class A {
  //private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/a/input7.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int a = Integer.parseInt(in.readLine());
      final int m = Integer.parseInt(in.readLine());
      final String line = in.readLine();
      System.out.print(calcHashNative(line, a, m));
    }
  }

  private static long calcHashNative(String line, int a, int m) {
    final int firstIndex = 0;
    final int lastIndex = line.length() - 1;
    if (firstIndex == lastIndex) {
      return line.charAt(firstIndex) % m;
    }

    long sum = line.charAt(firstIndex) * a;
    for (int i = 1; i < line.length() - 1; i++) {
      sum = hashGorner(sum, a, line.charAt(i));
    }
    sum += (line.charAt(line.length() - 1));

    return sum % m;
  }

  private static long calcHash(String line, int a, int m) {
    if (line.isEmpty()) {
      return 0;
    }
    // ( a + b * c) % m == (a % m + ((b % m) * (c % m) % m)) % m
    // a^3 mod p = ((a mod p)*(a mod p)*(a mod p)) mod p
    final int firstIndex = 0;
    final int lastIndex = line.length() - 1;
    final int aModM = a % m;
    if (firstIndex == lastIndex) {
      return line.charAt(firstIndex) % m;
    }

    long sum = ((long) (line.charAt(firstIndex) % m) * (aModM)) % m;
    for (int i = 1; i < lastIndex; i++) {
      sum = hashGorner(sum % m, aModM, line.charAt(i) % m) % m;
    }
    sum += (line.charAt(lastIndex)) % m;
    return sum % m;
  }

  private static long hashGorner(long sum, int a, long s) {
    return (sum + s) * a;
  }

  private static long hash(int a, int m, String[] s) {
    int d1 = 0;
    for (int i = 0; i < s.length; i++) {
      final int j = s[i].charAt(0);
      d1 += j * Math.pow(a, s.length - (i + 1));
    }
    return d1 % m;
  }
}
