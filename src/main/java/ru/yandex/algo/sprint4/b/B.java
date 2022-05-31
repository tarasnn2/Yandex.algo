package ru.yandex.algo.sprint4.b;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;

public class B {

  public static void main(String[] args) {
    final int a = 1000;
    final int m = 123_987_123;

    final String line1 = "ewrfvotofc";
    final long hash1 = hashByHornersMethod(line1, a, m);

    boolean flag = true;

    while (flag) {
      final String line2 = getRandomString(line1.length());
      //System.out.println(line2);
      final long hash2 = hashByHornersMethod(line2, a, m);
      if (hash1 == hash2) {
        System.out.println(line2);
        flag = false;
      }
    }
  }

  private static String getRandomString(int length) {
    return RandomStringUtils.randomAlphabetic(length).toLowerCase();
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
      return line.charAt(firstIndex) % m;
    }
    final int aModM = a % m;
    long sum = ((long) (line.charAt(firstIndex) % m) * (aModM)) % m;
    for (int i = 1; i < lastIndex; i++) {
      sum = hornersMethodInner(sum % m, aModM, line.charAt(i) % m) % m;
    }
    sum += (line.charAt(lastIndex)) % m;
    return sum % m;
  }

  private static long hornersMethodInner(long sum, int a, int s) {
    return (sum + s) * a;
  }
}
