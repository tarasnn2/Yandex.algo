package ru.yandex.algo.sprint8.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class C {

  //private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/c/input03.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      String line = in.readLine();
      System.out.println(make(doCount(line)));
    }
  }

  private static Map<String, Integer> doCount(String line) {
    final Map<String, Integer> count = new TreeMap<>(Comparator.naturalOrder());
    for (String s : line.split("")) {
      int c = count.getOrDefault(s, 0);
      c++;
      count.put(s, c);
    }
    return count;
  }

  private static String make(Map<String, Integer> count) {
    final StringBuilder result = new StringBuilder();
    final String[] center = {""};
    count.forEach((s, c) -> {
      if (c % 2 == 0) {
        result.append(String.valueOf(s).repeat(Math.max(0, c / 2)));
      } else {
        if (center[0].isEmpty()) {
          center[0] = s;
        }
        if (c > 1) {
          result.append(String.valueOf(s).repeat(Math.max(0, (c - 1) / 2)));
        }
      }
    });
    final StringBuilder reversResult = new StringBuilder(result).reverse();
    return result.append(center[0]).append(reversResult).toString();
  }

}
