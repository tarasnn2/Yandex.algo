package ru.yandex.algo.sprint4.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class C1 {
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/c/input00.txt";
  //private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.US_ASCII))) {
      final int base = Integer.parseInt(in.readLine());
      final int mod = Integer.parseInt(in.readLine());
      final String str = in.readLine();
      long[] prefixHash = getPrefixHash(str, base, mod);
      long[] powers = getPowers(str, base, mod);

      final int requestCount = Integer.parseInt(in.readLine());
      StringBuilder sb = new StringBuilder(requestCount);
      for (int i = 0; i < requestCount; i++) {
        final String[] request = in.readLine().split(" ");
        final int start = Integer.parseInt(request[0]);
        final int end = Integer.parseInt(request[1]);
        final long hash = getHash(prefixHash, powers, start, end, mod);
        sb.append(hash).append("\n");
      }
      System.out.print(sb);
    }
  }

  private static long[] getPrefixHash(String str, int base, int mod) {
    long[] prefixHashes = new long[str.length() + 1];
    for (int i = 1; i <= str.length(); ++i) {
      prefixHashes[i] = (prefixHashes[i - 1] * base % mod + str.charAt(i - 1)) % mod;
    }
    return prefixHashes;
  }

  private static long[] getPowers(String str, int base, int mod) {
    long[] powers = new long[str.length() + 1];
    powers[0] = 1;
    for (int i = 1; i <= str.length(); ++i) {
      powers[i] = (powers[i - 1] * base) % mod;
    }
    return powers;
  }

  private static long getHash(long[] prefixHashes, long[] powers, int start, int end, int mod) {
    return (prefixHashes[end] + mod - (prefixHashes[start - 1] * powers[end - start + 1]) % mod) % mod;
  }
}
