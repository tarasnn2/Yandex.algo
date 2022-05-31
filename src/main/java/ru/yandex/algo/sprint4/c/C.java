package ru.yandex.algo.sprint4.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class C {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/c/input00.txt";
  // private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    test();
    execute();
  }

  private static void execute() throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int a = Integer.parseInt(in.readLine());
      final int m = Integer.parseInt(in.readLine());
      final String line = in.readLine();
      final int maxIndex = line.length() - 1;
      final Map<Integer, Long> index = indexing2(line, a, m, maxIndex);

      final int requestCount = Integer.parseInt(in.readLine());
      StringBuilder sb = new StringBuilder(requestCount);
      for (int i = 0; i < requestCount; i++) {
        final String[] request = in.readLine().split(" ");
        final int startIndex = Integer.parseInt(request[0]) - 1;
        int endIndex = Integer.parseInt(request[1]) - 1;
        calcHash(startIndex, endIndex, a, m, index, maxIndex, sb);
      }
      System.out.print(sb);
    }
  }

  private static StringBuilder calcHash(
      int startIndex, int endIndex, int a, int m, Map<Integer, Long> index, int maxIndex, StringBuilder sb) {
    System.out.println("startIndex " + startIndex + " endIndex " + endIndex);
    final long fullHash = index.get(endIndex);
    if (startIndex == 0) {
      return sb.append(fullHash).append("\n");
    }
    final long prefixHash = index.get(startIndex - 1);
    final int exp = endIndex - startIndex;

    final long suffixHash = getSuffixHash(fullHash, prefixHash, exp, a, m);
    return sb.append(suffixHash).append("\n");
  }

  private static long getSuffixHash(long fullHash, long prefixHash, int exp, int a, int m) {
    // final long prefixHash = (long) (((index.get(endIndex) - index.get(startIndex - 1))%m) / Math.pow(a, endIndex)%m)%m;
    // final long prefixHash = (long) (((index.get(endIndex) - index.get(startIndex - 1))%m) / Math.pow(a, endIndex)%m)%m;
    // final long prefixHash = (long) (((index.get(endIndex) % m - index.get(startIndex) % m) % m) / Math.pow(a % m, endIndex) % m);

    // final long result = ((minus) % m + m) % m;
    // final long result = ((h.get(r - h.get(l) * p[r - l]) % m + m) % m);

    //final long pow = (long) Math.pow(a, exp);
    // final long pow = BigInteger.valueOf(a).modPow(BigInteger.valueOf(exp), BigInteger.valueOf(m)).longValue();
    // return (((fullHash - (prefixHash * pow)) % m) + m) % m;

    /*
    final long multiple = (prefixHash % m * pow) % m;
    final long minus = (fullHash % m - multiple % m) % m;
    return minus;*/

    // ( a + b * c) % m == (a % m + ((b % m) * (c % m) % m)) % m
    // return (fullHash % m - ((prefixHash % m * pow % m)) % m) % m;
    // return fullHash - (prefixHash * pow);

    // return ((fullHash%m - ((prefixHash %m ))/pow)%m)%m;

    // return (fullHash + m - (prefixHash * exp) % m) % m;

    final long pow = (long) Math.pow(a, exp) %m;
    return (fullHash + m - (prefixHash * pow) % m) % m;
  }

  private static Map<Integer, Long> indexing2(String str, int base, int mod, int maxIndex) {
    Map<Integer, Long> index = new HashMap<>(str.length() + 1);
    index.put(0, 0L);
    for (int i = 1; i <= str.length(); ++i) {
      final long h = (index.get(i - 1) * base % mod + (str.charAt(i - 1))) % mod;
      index.put(i, h);
      System.out.println("i=" + i + " hash=" + h);
    }
    return index;
  }

  private static Map<Integer, Long> indexing(String line, int a, int m, int maxIndex) {
    Map<Integer, Long> index = new HashMap<>(line.length());
    for (int i = 0; i <= line.length(); i++) {
      final String s = line.substring(0, i);
      final long h = hashByHornersMethod(s, a, m);
      index.put(i, h);
      System.out.println("i=" + i + " s=" + s + " hash=" + h);
    }
    return index;
  }

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

  private static void test() {
    {
      System.out.println(hashByHornersMethod("cd", 1000, 1000009));
    }
    /*    {
      // 4 4
      System.out.println(hashByHornersMethod("e", 1000));
      System.out.println(hashByHornersMethod("e", 1000, 1000009));
    }*/
  }
}
