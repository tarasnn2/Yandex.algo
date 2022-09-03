package ru.yandex.algo.sprint8.f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class F {

  // private static final String FILE = "input.txt";
  private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint8/f/input03.txt";

  public static void main(String[] args) throws IOException {
    try (final BufferedReader in = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {

      final int n = Integer.parseInt(in.readLine());
      Map<String, Integer> counter = new HashMap<>();

      for (int i = 0; i < n; i++) {
        String word = in.readLine();
        Integer count = counter.getOrDefault(word, 0);
        count++;
        counter.put(word, count);
      }

      System.out.println(getMaxFreqString(counter));

    }
  }

  private static String getMaxFreqString(Map<String, Integer> counter) {
    final String[] str = {""};
    final int[] frequency = {0};
    counter.forEach((word, count) -> {
      if (count > frequency[0]) {
        str[0] = word;
        frequency[0] = count;
      }
      if (count == frequency[0] && str[0].compareTo(word) > 0) {
        str[0] = word;
      }
    });
    return str[0];
  }

}


