package ru.yandex.algo.sprint4.d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class D {
  // private static final String FILE = "/home/taras/repoMy/projects/Yandex.algo/src/main/java/ru/yandex/algo/sprint4/d/input01.txt";
  private static final String FILE = "input.txt";

  public static void main(String[] args) throws IOException {
    execute();
  }

  private static void execute() throws IOException {
    try (final BufferedReader in =
        new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FILE)), StandardCharsets.UTF_8))) {
      final int clubCount = Integer.parseInt(in.readLine());
      int i = 0;
      Map<String, Integer> map = new HashMap<>(clubCount);
      StringBuilder result = new StringBuilder();
      while (i < clubCount) {
        final String clubName = in.readLine();
        Integer count = map.getOrDefault(clubName, 0);
        if (0 == count) {
          result.append(clubName).append("\n");
        }
        map.put(clubName, ++count);
        i++;
      }
      System.out.print(result);
    }
  }
}
